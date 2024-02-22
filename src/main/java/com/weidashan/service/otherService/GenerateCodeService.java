package com.weidashan.service.otherService;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateCodeService {

    public String generateCode(){

        return generateCode(6);
    }
    public String generateCode(int number){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<number;i++){
            stringBuilder.append(new Random().nextInt(10));
        }
        return stringBuilder.toString();
    }

}
