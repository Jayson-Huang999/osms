package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.common.utls.SnowflakeIdUtil;
import com.example.mybatisplus.mapper.AddressMapper;
import com.example.mybatisplus.mapper.KillGoodsMapper;
import com.example.mybatisplus.mapper.OrdersMapper;
import com.example.mybatisplus.model.domain.KillOrders;
import com.example.mybatisplus.mapper.KillOrdersMapper;
import com.example.mybatisplus.service.KillGoodsService;
import com.example.mybatisplus.service.KillOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hhf
 * @since 2022-05-25
 */
@Service
public class KillOrdersServiceImpl extends ServiceImpl<KillOrdersMapper, KillOrders> implements KillOrdersService {
    @Autowired
    private KillOrdersMapper killOrdersMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private KillGoodsMapper killGoodsMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    private SnowflakeIdUtil idUtil=new SnowflakeIdUtil(0,0);

    @Override
    public List<Map<String, Object>> getOrderInfo() {
        return killOrdersMapper.getOrdersInfo();
    }

    @Transactional
    @Override
    public void addOrder(Long id,Long userId, Long goodsId,Long addId, BigDecimal price, LocalDateTime now) {
        killOrdersMapper.addKillOrder(id,userId, goodsId, addId, price, now);
    }
}
