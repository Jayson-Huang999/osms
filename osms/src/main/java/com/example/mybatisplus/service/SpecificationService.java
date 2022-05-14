package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Specification;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品规格 服务类
 * </p>
 *
 * @author zp
 * @since 2022-03-03
 */
public interface SpecificationService extends IService<Specification> {

    void updateByPid(Object id, Object size, Object price, Object stock);
}
