package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.mapper.KillGoodsMapper;
import com.example.mybatisplus.model.domain.Activity;
import com.example.mybatisplus.mapper.ActivityMapper;
import com.example.mybatisplus.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author tyl
 * @since 2022-03-09
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    @Autowired
    private KillGoodsMapper killGoodsMapper;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void updateKillGoodsInfo() {
        //商品信息加库存
        List<Map<String,Object>> goodsInfo = killGoodsMapper.getGoodsInfo();
        if(goodsInfo.isEmpty()){
            return ;
        }
        for(Map<String,Object> map:goodsInfo){
            //获取分布式锁，使用库存作为信号量
            RSemaphore semaphore = redissonClient.getSemaphore("kill_goods"+map.get("id"));
            semaphore.trySetPermits((int)map.get("stock"));//设置信号量：能秒杀商品数量
        }
    }
}
