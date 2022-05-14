package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.domain.Specification;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品规格 Mapper 接口
 * </p>
 *
 * @author zp
 * @since 2022-03-03
 */
@Repository
public interface SpecificationMapper extends BaseMapper<Specification> {

    void updateByPid(Object id, Object size, Object price, Object stock);

    int getTotalStockByPid(Long id);

    void updateSpecificationStock(Long specification_id, Integer num);
}
