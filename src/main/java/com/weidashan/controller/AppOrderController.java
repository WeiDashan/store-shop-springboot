package com.weidashan.controller;


import com.weidashan.pojo.AppOrder;
import com.weidashan.pojo.PmsStock;
import com.weidashan.service.IAppOrderService;
import com.weidashan.service.IPmsStockService;
import com.weidashan.service.otherService.RabbitMQService;
import com.weidashan.util.ResultJson;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weidashan
 * @since 2024-02-22
 */
@RestController
@RequestMapping("/app-order")
public class AppOrderController {

    @Resource
    RabbitMQService rabbitMQService;

    @Resource
    IPmsStockService pmsStockService;

    @Resource
    IAppOrderService appOrderService;

    @PostMapping("/getAllOrdersByUserId")
    ResultJson getAllOrdersByUserId(Long userId){
        return ResultJson.success(appOrderService.getAllOrdersByUserId(userId),"请求订单成功");
    }

    @PostMapping("/getAllOrdersByUserIdAndStatus")
    ResultJson getAllOrdersByUserIdAndStatus(Long userId, Integer orderStatus){
        return ResultJson.success(appOrderService.getAllOrdersByUserIdAndStatus(userId, orderStatus),"请求订单成功");
    }

    @PostMapping("/createOrder")
    ResultJson createOrder(AppOrder appOrder){
//        System.out.println("sendOrderToMQ appOrder"+appOrder.toString());
        //判断库存
        PmsStock pmsStock = pmsStockService.getById(appOrder.getStockId());
        int mysqlStock = pmsStock.getStock();
        int orderNum = appOrder.getProductNum();
        if (orderNum > mysqlStock) {
            //库存数量不够
            return ResultJson.error("库存不够，订单无法生成");
        }

        //库存扣除
        pmsStock.setStock(mysqlStock-orderNum);
        pmsStockService.updateById(pmsStock);


        //生成订单
        appOrder.setCreateTime(LocalDateTime.now());
        boolean flag = appOrderService.save(appOrder);

        if (!flag){
            return ResultJson.error("订单无法生成");
        }
//        //异步消息
//        rabbitMQService.sendOrder(appOrder);
        return ResultJson.success(1,"订单创建成功");
    }


    /**
     * 推送下单信息至MQ
     * @param appOrder 订单信息
     * @return 返回订单生成
     */
    @PostMapping("/sendOrderToMQ")
    ResultJson sendOrderToMQ(AppOrder appOrder){
        return ResultJson.success(1,"订单创建成功");
    }

}
