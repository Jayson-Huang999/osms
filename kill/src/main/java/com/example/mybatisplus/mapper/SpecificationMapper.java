package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Specification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品规格 Mapper 接口
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
@Repository
public interface SpecificationMapper extends BaseMapper<Specification> {

    void decStock(Long specificationId);
}
