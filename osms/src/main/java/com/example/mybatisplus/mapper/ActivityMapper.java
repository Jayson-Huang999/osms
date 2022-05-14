package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表 Mapper 接口
 * </p>
 *
 * @author tyl
 * @since 2022-03-09
 */
@Repository
public interface ActivityMapper extends BaseMapper<Activity> {

    List<Map<String,Object>> getActivityProducts();

    Map<String, Object> getProductInfo(Long id);

    void kill(long id, Long timeStamp,int stock);

    void updateActivityRemain(Long activity_id, Integer num);
}
