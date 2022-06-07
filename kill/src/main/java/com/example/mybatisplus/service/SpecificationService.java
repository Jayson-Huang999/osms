package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Specification;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 商品规格 服务类
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
public interface SpecificationService extends IService<Specification> {

    void decStock(Long specificationId);
}
