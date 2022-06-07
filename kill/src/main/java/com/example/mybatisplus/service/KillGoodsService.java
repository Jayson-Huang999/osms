package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.KillGoods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
public interface KillGoodsService extends IService<KillGoods> {

    List<Map<String, Object>> getGoodsInfo();

    Long getGoodsStock(Long goodsId);

    void decrementStock(Long goodsId);

    void incrementStock(Long goodsId);

}
