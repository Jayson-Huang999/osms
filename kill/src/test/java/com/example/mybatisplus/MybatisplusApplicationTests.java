package com.example.mybatisplus;


import com.example.mybatisplus.mapper.AddressMapper;
import com.example.mybatisplus.service.KillOrdersService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class MybatisplusApplicationTests {
@Autowired
private AddressMapper addressMapper;
@Autowired
private KillOrdersService killOrdersService;
@Autowired
private RedissonClient redissonClient;
@Autowired
private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        RSemaphore semaphore = redissonClient.getSemaphore("kill_goods" + 13);
        semaphore.release();
        //删除redis的订单
        redisTemplate.delete("order:"+4+":"+13);
        redisTemplate.delete("killOrderInfo:"+4+":"+13);
    }

}
