package com.weidashan.service.otherService;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value, long minutesTimeout){
        redisTemplate.opsForValue().set(key, value, minutesTimeout, TimeUnit.MINUTES);
    }

    public String getString(String key){
        return (String)redisTemplate.opsForValue().get(key);
    }
}
