package com.weidashan.service.otherService;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Resource
    RedisScript<Long> script;

    public Long secKill(Long secKillId, Long userId){

        return  redisTemplate.execute(script, Collections.EMPTY_LIST, secKillId, userId);

    }
    public void set(String key, Object value, long minutesTimeout){
        redisTemplate.opsForValue().set(key, value, minutesTimeout, TimeUnit.MINUTES);
    }
    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    public void hashSet(String key, String hKey, Integer hValue){
        redisTemplate.opsForHash().put(key, hKey, hValue);
        redisTemplate.opsForHash().getOperations().expire(key, 600, TimeUnit.MINUTES);
    }
    public void hashSetPutAll(String key, Map map){
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.opsForHash().getOperations().expire(key, 600, TimeUnit.MINUTES);
    }
    public String getString(String key){
        return (String)redisTemplate.opsForValue().get(key);
    }

    public Integer getInteger(String key){
        return (Integer) redisTemplate.opsForValue().get(key);
    }

    public Integer hashGet(String key, String hKey){
        return (Integer) redisTemplate.opsForHash().get(key, hKey);
    }




}
