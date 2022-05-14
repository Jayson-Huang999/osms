package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Brand;
import com.example.mybatisplus.mapper.BrandMapper;
import com.example.mybatisplus.service.BrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品品牌信息表 服务实现类
 * </p>
 *
 * @author tyl
 * @since 2022-03-09
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

}
