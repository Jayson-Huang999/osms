package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Activity;
import com.example.mybatisplus.mapper.ActivityMapper;
import com.example.mybatisplus.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public void decStock(Long activityId) {
        activityMapper.decStock(activityId);
    }
}
