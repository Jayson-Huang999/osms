package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Activity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
public interface ActivityService extends IService<Activity> {

    void decStock(Long activityId);
}
