package com.weidashan.service.otherService;

import com.alibaba.fastjson.JSONObject;
import com.weidashan.pojo.AppOrder;
import com.weidashan.pojo.Email;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RabbitMQService {

    @Resource
    RabbitTemplate rabbitTemplate;

    public void sendCodeToEmail(String code, String emailTo){
        //设置邮件主体
        Email email = new Email();
        email.setSubject("获取验证码");
        email.setMessage("验证码："+code);
        email.setTo(emailTo);

        //设置邮件发送
        rabbitTemplate.convertAndSend("email", JSONObject.toJSONString(email));
    }

    public void sendOrder(AppOrder appOrder){
        rabbitTemplate.convertAndSend("order", JSONObject.toJSONString(appOrder));
    }

}
