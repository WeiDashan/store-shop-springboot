package com.weidashan.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weidashan.pojo.*;
import com.weidashan.service.IPmsProductService;
import com.weidashan.service.IPmsStockService;
import com.weidashan.service.ISecKillService;
import com.weidashan.service.otherService.RabbitMQService;
import com.weidashan.service.otherService.RedisService;
import com.weidashan.util.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 秒杀表 前端控制器
 * </p>
 *
 * @author weidashan
 * @since 2024-02-25
 */
@RestController
@RequestMapping("/sec-kill")
public class SecKillController {
    @Resource
    IPmsProductService productService;
    @Resource
    IPmsStockService stockService;
    @Resource
    ISecKillService secKillService;

    @Resource
    RedisService redisService;

    @Resource
    RabbitMQService rabbitMQService;

    /**
     * 抢购的主逻辑
     *
     * 参数：userId + secKillId
     *
     * 避免重复抢购的key：secKill_secKillId_user_userId
     *
     * @return
     */
    @PostMapping("/secKillNow")
    ResultJson secKillNow(Long userId, Long secKillId, Long stockId){
        System.out.println("userId: "+userId+" secKillId:"+secKillId+" stockId:"+stockId);

        //-- return 1表示成功
        //-- return 2表示库存不够
        //-- return 3表示无法重复抢购
        //-- return 4表示该秒杀信息丢失
        Long secKillResult = redisService.secKill(secKillId, userId);
        System.out.println("secKillResult: "+secKillResult);
        if (secKillResult==1){
//            MQ异步生成订单
            AppOrder appOrder = new AppOrder();
            appOrder.setStockId(stockId);
            appOrder.setUserId(userId);
            appOrder.setProductNum(1);
            rabbitMQService.sendOrder(appOrder);
            return ResultJson.success(1,"订单创建中，请稍后");
        } else if (secKillResult==2) {
            return ResultJson.error("库存不够，订单无法生成");
        } else if (secKillResult==3) {
            return ResultJson.error("无法重复抢购");
        } else{
            return ResultJson.error("秒杀超时");
        }
    }

    @GetMapping("/list")
    ResultJson list(Integer pageNo, Integer pageSize){
        IPage<SecKill> pages = secKillService.page(pageNo,pageSize);
        List<SecKill> secKills = pages.getRecords();
        List<PmsProduct> pmsProducts = new ArrayList<>();
        List<PmsStock> stocks = new ArrayList<>();
        for (SecKill secKill: secKills){
            pmsProducts.add(productService.getById(secKill.getProductId()));
            stocks.add(stockService.getById(secKill.getStockId()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("secKills", pages);
        map.put("pmsProducts", pmsProducts);
        map.put("stocks", stocks);
        return ResultJson.success(map,"请求成功");
    }

    /**
     *
     * (1) 预扣库存Mysql中stock库存
     * (2) 保存秒杀信息到Mysql中
     * (3) 将秒杀库存放置在Redis缓存中
     * @param secKill 秒杀设置信息
     * @return
     */
    @PostMapping("/saveSecKill")
    ResultJson saveSecKill(SecKill secKill){

        System.out.println(secKill.toString());

        //判断库存数量是否足够
        PmsStock mysqlStock = stockService.getById(secKill.getStockId());
        if (mysqlStock.getStock()<secKill.getSaleAmount()){
            return ResultJson.error("库存不足，无法设置秒杀");
        }
        mysqlStock.setStock(mysqlStock.getStock()-secKill.getSaleAmount());
        stockService.updateById(mysqlStock);

        // 保存秒杀信息到Mysql中
        LocalDateTime startTime = LocalDateTime.now();
        secKill.setStartTime(startTime);
        secKill.setEndTime(startTime);
        boolean flag = secKillService.save(secKill);

        /**
         * Redis缓存秒杀数据(key-hashmap)
         * KEY <=> secKillId
         * KEY={
         *     sale_amount,
         *     lock_amount,
         *     success_amount,
         * }
         */
        secKill = secKillService.getSecKillByStockId(secKill.getStockId());
        String key = "secKill_"+secKill.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("sale_amount", secKill.getSaleAmount());
        map.put("lock_amount", 0);
        map.put("success_amount", 0);
        redisService.hashSetPutAll(key, map);
        return ResultJson.success(flag,"保存成功");
    }

    @GetMapping("/getAllStockOrderByProduct")
    ResultJson getAllStockOrderByProduct(){
        List<PmsProduct> productList = productService.list();
        List<PmsStock> stockList = stockService.list();
        for (PmsStock stock: stockList){
            for (PmsProduct product: productList){
                if (stock.getProductId()==product.getId()){
                    List<PmsStock> list = product.getChildren();
                    if (list == null){
                        list = new ArrayList<>();
                    }
                    list.add(stock);
                    product.setChildren(list);
                    break;
                }
            }
        }
        return ResultJson.success(productList,"获取成功");
    }

    /**
     * 移动端首页中，展示秒杀专栏相关信息：secKillId, productName, stock--->price, productImg
     * @return
     */
    @GetMapping("/getSecKillDetail")
    ResultJson getSecKillDetail(){
        List<SecKill> secKills = secKillService.list();
        List<PmsProduct> products = new ArrayList<>();
        List<PmsStock> stocks = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        for (SecKill secKill: secKills){
            long productId = secKill.getProductId();
            long stockId = secKill.getStockId();
            products.add(productService.getById(productId));
            stocks.add(stockService.getById(stockId));
        }

        map.put("secKills",secKills);
        map.put("stocks", stocks);
        map.put("products", products);

        return ResultJson.success(map, "请求成功");
    }
}
