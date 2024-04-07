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

//    @Resource
//    RedisScript<Long> script;

    public Long secKill(Long secKillId, Long userId){
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("scripts/secKill.lua"));
        redisScript.setResultType(Long.class);
        return  redisTemplate.execute(redisScript, Collections.EMPTY_LIST, secKillId, userId);

    }
    public void set(String key, Object value, long minutesTimeout){
        redisTemplate.opsForValue().set(key, value, minutesTimeout, TimeUnit.MINUTES);
    }
    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key, value, 49999999, TimeUnit.SECONDS);
    }
    public void hashSetPutAll(String key, Map map){
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.opsForHash().getOperations().expire(key, 49999999, TimeUnit.SECONDS);
    }
    public String getString(String key){
        return (String)redisTemplate.opsForValue().get(key);
    }
    public boolean isExists(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    public void del(String key){
        redisTemplate.delete(key);
    }
}
