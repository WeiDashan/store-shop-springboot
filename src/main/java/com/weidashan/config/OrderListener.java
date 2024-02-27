package com.weidashan.config;

import com.alibaba.fastjson.JSONObject;
import com.weidashan.pojo.AppOrder;
import com.weidashan.pojo.PmsProduct;
import com.weidashan.pojo.PmsStock;
import com.weidashan.service.IAppOrderService;
import com.weidashan.service.IPmsProductService;
import com.weidashan.service.IPmsStockService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class OrderListener {

    @Resource
    IAppOrderService appOrderService;
    @Resource
    IPmsStockService stockService;
    @Resource
    IPmsProductService productService;
    @Value("${minio.endpoint}")
    String minioUrl;

    @RabbitListener(queues = "order")
    public void createOrder(Message message){
        AppOrder appOrder = JSONObject.parseObject(new String(message.getBody()),AppOrder.class);
        appOrder.setCreateTime(LocalDateTime.now());
        PmsStock stock = stockService.getById(appOrder.getStockId());
        appOrder.setSkuDetail(stock.getSkuList());
        appOrder.setProductPrice(stock.getPrice().setScale(0, BigDecimal.ROUND_HALF_UP)
                .intValue()*100);
        appOrder.setProductId(stock.getProductId());
        PmsProduct product = productService.getById(stock.getProductId());
        appOrder.setProductName(product.getName());
        appOrder.setProductIcon(minioUrl+"images/"+product.getImg());
        appOrderService.save(appOrder);
        System.out.println("createOrder: "+appOrder.toString());
    }

}
