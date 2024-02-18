package com.weidashan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.weidashan.mapper")
public class NeuShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(NeuShopApplication.class, args);
    }

}
