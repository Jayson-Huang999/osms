package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Specification;
import com.example.mybatisplus.mapper.SpecificationMapper;
import com.example.mybatisplus.service.SpecificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品规格 服务实现类
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements SpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;
    @Override
    public void decStock(Long specificationId) {
        specificationMapper.decStock(specificationId);
    }
}
