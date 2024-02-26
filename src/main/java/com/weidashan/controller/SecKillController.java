package com.weidashan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weidashan.pojo.PmsProduct;
import com.weidashan.pojo.PmsStock;
import com.weidashan.pojo.SecKill;
import com.weidashan.pojo.UmsUser;
import com.weidashan.service.IPmsProductService;
import com.weidashan.service.IPmsStockService;
import com.weidashan.service.ISecKillService;
import com.weidashan.util.ResultJson;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 抢购的主逻辑
     *
     * 参数：userId + secKillId
     *
     * @return
     */
    @PostMapping("/secKillNow")
    ResultJson secKillNow(Long userId, Long secKillId, Long stockId){
        System.out.println("userId: "+userId+" secKillId:"+secKillId+" stockId:"+stockId);
        //判断库存
//        PmsStock pmsStock = pmsStockService.getById(stockId);
//        int mysqlStock = pmsStock.getStock();
//        int orderNum = appOrder.getProductNum();
//        if (orderNum > mysqlStock) {
//            //库存数量不够
//            return ResultJson.error("库存不够，订单无法生成");
//        }

        // 秒杀还需要判断是否重复抢购

        //库存扣除
//        pmsStock.setStock(mysqlStock-orderNum);
//        pmsStockService.updateById(pmsStock);


        //生成订单
//        appOrder.setCreateTime(LocalDateTime.now());
//        boolean flag = appOrderService.save(appOrder);

//        if (!flag){
//            return ResultJson.error("订单无法生成");
//        }
//        //异步消息
//        rabbitMQService.sendOrder(appOrder);
        return ResultJson.success(1,"订单创建成功");
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

    @PostMapping("/saveSecKill")
    ResultJson saveSecKill(SecKill secKill){
        secKill.setStartTime(LocalDateTime.now());
        secKill.setEndTime(LocalDateTime.now());
        return ResultJson.success(secKillService.save(secKill));
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
