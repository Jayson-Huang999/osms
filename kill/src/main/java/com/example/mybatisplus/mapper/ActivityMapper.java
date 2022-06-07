package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 活动表 Mapper 接口
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    void decStock(Long activityId);
}
