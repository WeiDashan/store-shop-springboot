package com.weidashan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    //邮件主题
    String subject;
    //邮件接收方
    String to;
    //发送的信息
    String message;
}
