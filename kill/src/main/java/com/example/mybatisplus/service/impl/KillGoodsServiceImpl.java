package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.KillGoods;
import com.example.mybatisplus.mapper.KillGoodsMapper;
import com.example.mybatisplus.service.KillGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
@Service
public class KillGoodsServiceImpl extends ServiceImpl<KillGoodsMapper, KillGoods> implements KillGoodsService {
    @Autowired
    private KillGoodsMapper killGoodsMapper;

    @Override
    public List<Map<String, Object>> getGoodsInfo() {
        return killGoodsMapper.getGoodsInfo();
    }

    @Override
    public Long getGoodsStock(Long goodsId) {
        return killGoodsMapper.getGoodsStock(goodsId);
    }

    @Override
    public void decrementStock(Long goodsId) {
        killGoodsMapper.decrementStock(goodsId);
    }

    @Override
    public void incrementStock(Long goodsId) {
        killGoodsMapper.incrementStock(goodsId);
    }


}
