package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.service.RedisService;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisTemplate redisTemplate;

    /*
     * 设置key并且设置有效时间
     * */
    public void set(String key, Object value) {
        //更改在redis里面查看key编码问题
        RedisSerializer redisSerializer =new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            this.delete(key);
        }
        vo.set(key, value,2,TimeUnit.MINUTES);//设置key并且设置有效时间
    }
    /*
     * 删除key
     * */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
    /*
     * 更新key
     * */
    public  Boolean update(String key){

        return redisTemplate.expire(key, 2, TimeUnit.MINUTES);
    }
    /*
     * 查询key
     * */
    public Object get(String key) {
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        return vo.get(key);
    }
}
