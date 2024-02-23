package com.weidashan.config;

import com.alibaba.fastjson.JSONObject;
import com.weidashan.pojo.AppOrder;
import com.weidashan.service.IAppOrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
public class OrderListener {

    @Resource
    IAppOrderService appOrderService;

    @RabbitListener(queues = "order")
    public void createOrder(Message message){
        AppOrder appOrder = JSONObject.parseObject(new String(message.getBody()),AppOrder.class);
        appOrder.setCreateTime(LocalDateTime.now());
        appOrderService.save(appOrder);
//        System.out.println("createOrder: "+appOrder.toString());
    }

}
