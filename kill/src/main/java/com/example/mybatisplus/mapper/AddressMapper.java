package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Address;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 收货地址信息表 Mapper 接口
 * </p>
 *
 * @author hhf
 * @since 2022-05-25
 */
@Repository
public interface AddressMapper extends BaseMapper<Address> {

    Long getDefaultAdd(Long userId);
}
