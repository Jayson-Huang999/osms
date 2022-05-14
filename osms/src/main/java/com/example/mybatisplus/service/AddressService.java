package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.domain.Address;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * �ջ���ַ��Ϣ�� 服务类
 * </p>
 *
 * @author lgl
 * @since 2022-03-01
 */
public interface AddressService extends IService<Address> {

    List<Address> getByUserId(Long userId);

    void addAddress(Address address);

    int updateAddress(Address address);

    void setDefault(Long id, Address address);

    Address getByAddId(Long id);

    Address getDefault(Long userId);

    List<Map<String, Object>> getAlladdress(Long userId);
}
