package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.KillGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
@Repository
public interface KillGoodsMapper extends BaseMapper<KillGoods> {

    List<Map<String, Object>> getGoodsInfo();

    Long getGoodsStock(Long goodsId);

    void decrementStock(Long goodsId);

    void incrementStock(Long goodsId);
}
