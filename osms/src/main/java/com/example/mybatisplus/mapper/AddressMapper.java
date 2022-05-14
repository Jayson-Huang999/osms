package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.domain.Address;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * �ջ���ַ��Ϣ�� Mapper 接口
 * </p>
 *
 * @author lgl
 * @since 2022-03-01
 */
@Repository
public interface AddressMapper extends BaseMapper<Address> {

    List<Address> selectByUserId(Long userId);

    void addAddress(Address address);

    int updateAddress(Address address);

    void setDefault(Long id);

    Address selectByAddId(Long id);

    Address getDefault(Long userId);

    void setNotDefault(Address address);

    List<Map<String, Object>> getAlladdress(Long userId);
}
