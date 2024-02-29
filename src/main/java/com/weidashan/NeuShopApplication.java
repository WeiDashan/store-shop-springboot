package com.weidashan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.weidashan.mapper")
@ServletComponentScan(basePackages = "com.weidashan.config")
public class NeuShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(NeuShopApplication.class, args);
    }

}
