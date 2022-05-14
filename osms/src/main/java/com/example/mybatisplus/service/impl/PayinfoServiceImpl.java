package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Payinfo;
import com.example.mybatisplus.mapper.PayinfoMapper;
import com.example.mybatisplus.service.PayinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付信息表 服务实现类
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
@Service
public class PayinfoServiceImpl extends ServiceImpl<PayinfoMapper, Payinfo> implements PayinfoService {

}
