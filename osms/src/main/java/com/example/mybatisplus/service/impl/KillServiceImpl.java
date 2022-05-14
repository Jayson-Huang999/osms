package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.mybatisplus.common.utls.RedisLockUtils;
import com.example.mybatisplus.mapper.ActivityMapper;
import com.example.mybatisplus.mapper.OrdersMapper;
import com.example.mybatisplus.mapper.ProductMapper;
import com.google.common.util.concurrent.RateLimiter;
import lombok.Synchronized;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class KillServiceImpl {
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    private RedisLockUtils redisLock = new RedisLockUtils(stringRedisTemplate);
    //将库存写进redis
    public void setKillProduct(Long id,int quantity,Long time) {
        //更改在redis里面查看key编码问题
        RedisSerializer redisSerializer =new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(id.toString())) {
            this.delete(id.toString());
        }
        vo.set(id.toString(), quantity,time, TimeUnit.SECONDS);//设置key并且设置有效时间
    }
    //将用户写入redis
    public void setKillUsers(Long id,List<Long> users) {
        RedisSerializer redisSerializer =new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        ListOperations<String, List<Long>> vo = redisTemplate.opsForList();
        if (redisTemplate.hasKey("u"+id.toString())) {
            this.delete("u"+id.toString());
        }
        vo.rightPushAll("u"+id.toString(),users);
    }
    //第一件事情：限制访问数量,接口限流,写在controller
    //第二件事情：判断用户是否购买过该商品
    public boolean checkUser(Long id, Long userId){
        String token = null;
        Boolean hasUser = false;
        try{
            token = redisLock.lock("users"+id.toString(), 10000, 11000);
            if(token != null) {
                System.out.println("该用户拿到了锁哦");
                // 执行业务代码
                RedisSerializer redisSerializer =new StringRedisSerializer();
                redisTemplate.setKeySerializer(redisSerializer);
                ListOperations<String, List<Long>> vo = redisTemplate.opsForList();
                if(redisTemplate.hasKey("u"+userId.toString())){
                    List users = vo.range("u"+id.toString(),0,-1);
                    if(users.contains(userId)) {
                        hasUser = true;
                    }
                } else {
                    //从数据库读数据并存到redis中
                    this.getUsersInfo(id);
                    List users = vo.range("u"+id.toString(),0,-1);
                    if(users.contains(userId)) {
                        hasUser = true;
                    }
                }
            } else {
                System.out.println("该用户没有拿到锁唉");
            }
        } finally {
            if(token!=null) {
                redisLock.unlock("users"+id.toString(), token);
            }
            return hasUser;
        }
    }
    //第三件事情判断库存是否够用，够用的话直接抢购成功
    public Map<String,Object> checkStock(Long id, Long userId){
        String token = null;
        Map<String,Object> message = new HashMap<>();
        try{
            token = redisLock.lock("product"+id.toString(), 10000, 11000);
            if(token != null) {
                System.out.println("该用户拿到了锁哦");
                // 执行业务代码
                RedisSerializer redisSerializer =new StringRedisSerializer();
                redisTemplate.setKeySerializer(redisSerializer);
                ValueOperations<String,Object> vo = redisTemplate.opsForValue();
                if (redisTemplate.hasKey(id.toString())) {
                    this.delete(id.toString());
                }else {
                    //从数据库读取商品信息
                    this.getProductInfo(id);
                }
                int stock = (int)vo.get(id.toString()) ;
                if((int)vo.get(id.toString()) > 0){
                    //秒杀成功,并提醒用户
                    message.put("hasStock",true);
                    //更新redis
                    Long time = redisTemplate.getExpire(id.toString());
                    Date date = new Date(Instant.now().toEpochMilli() + time * 1000);
                    stock--;
                    vo.set(id.toString(),stock);
                    redisTemplate.expireAt(id.toString(), date);
                    message.put("stock",stock);
                    message.put("timeStamp",System.currentTimeMillis());
                } else {
                    message.put("hasStock",false);
                }
            } else {
                System.out.println("该用户没有拿到锁唉");
            }
        } finally {
            if(token!=null) {
                redisLock.unlock("product"+id.toString(), token);
            }
            return message;
        }
    }
    //第四件事情：交给，创建订单
    public void handleOrder(Long userId, Long id,int stock,Long timeStamp) {

    }
    //第五件事情：取消订单订单使得库存加1,加锁，更新数据库要修改timeStamp
    public void cancelOrder(Long userId, Long id,Long timeStamp,int stock) {
        //更新订单

        //更新库存
        String token = null;
        try{
            token = redisLock.lock("product"+id.toString(), 10000, 11000);
            if(token != null) {
                System.out.println("该用户拿到了锁哦");
                // 执行业务代码
                RedisSerializer redisSerializer =new StringRedisSerializer();
                redisTemplate.setKeySerializer(redisSerializer);
                ValueOperations<String,Object> vo = redisTemplate.opsForValue();
                stock = (int) vo.get(id.toString());
                stock++;
                vo.set(id.toString(),stock);
                timeStamp = System.currentTimeMillis();
            } else {
                System.out.println("该用户没有拿到锁唉");
            }
        } finally {
            if(token!=null) {
                redisLock.unlock("product"+id.toString(), token);
            }
        }
        //更新数据库
        activityMapper.kill(id,timeStamp,stock);
    }
    //确认订单
    public void confirmOrder(Long userId,Long id,Long timeStamp,int stock){
        //更新订单

        //更新数据库
        activityMapper.kill(id,timeStamp,stock);
    }
    //从数据库读取商品信息，并存到redis,activity表pid,endtime,quantity
    public void getProductInfo(Long id){
        Map<String,Object> info = activityMapper.getProductInfo(id);
        Timestamp timestamp = (Timestamp)info.get("endtime");
        LocalDateTime date= timestamp.toLocalDateTime();
        int remain = (int) info.get("remain");
        Duration duration = Duration.between(date,LocalDateTime.now());
        Long time = duration.toMillis()/1000;
        this.setKillProduct(id,remain,time);
    }
    //从数据库获取订单信息，并存到redis,pid+uid,list
    public void getUsersInfo(Long id){
        List<Long> users = ordersMapper.getUserInfo(id);
        this.setKillUsers(id,users);
    }
    /*
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
