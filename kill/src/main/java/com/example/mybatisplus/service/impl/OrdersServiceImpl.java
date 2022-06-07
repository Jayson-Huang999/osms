package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Orders;
import com.example.mybatisplus.mapper.OrdersMapper;
import com.example.mybatisplus.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author hhf
 * @since 2022-05-31
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
