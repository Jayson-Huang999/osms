package com.example.mybatisplus.mapper;

import com.example.mybatisplus.common.utls.SnowflakeIdUtil;
import com.example.mybatisplus.model.domain.KillOrders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hhf
 * @since 2022-05-25
 */
@Repository
public interface KillOrdersMapper extends BaseMapper<KillOrders> {

    List<Map<String, Object>> getOrdersInfo();

    void addKillOrder(Long id,Long userId, Long goodsId,Long addressId, BigDecimal price, LocalDateTime now);

}
