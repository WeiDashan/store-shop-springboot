package com.weidashan.config;

import com.alibaba.fastjson.JSONObject;
import com.weidashan.pojo.Email;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Component

public class EmailListener {
    @Resource
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    String emailFrom;

    // 监听RabbitMQ的email队列，获取到message后处理
    @RabbitListener(queues = "email")
    public void handler(Message message){
        Email email = JSONObject.parseObject(
                new String(message.getBody(), StandardCharsets.UTF_8),
                Email.class);
        SimpleMailMessage sendMessage = new SimpleMailMessage();
        sendMessage.setText(email.getMessage());
        sendMessage.setFrom(emailFrom);
        sendMessage.setTo(email.getTo());
        sendMessage.setSubject(email.getSubject());
        javaMailSender.send(sendMessage);
    }
}
