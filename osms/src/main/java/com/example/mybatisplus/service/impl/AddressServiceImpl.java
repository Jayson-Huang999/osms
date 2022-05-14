package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.mapper.AddressMapper;
import com.example.mybatisplus.model.domain.Address;
import com.example.mybatisplus.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * �ջ���ַ��Ϣ�� 服务实现类
 * </p>
 *
 * @author lgl
 * @since 2022-03-01
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> getByUserId(Long userId) {
        return addressMapper.selectByUserId(userId);
    }

    @Override
    public void addAddress(Address address) {
        addressMapper.addAddress(address);
    }

    @Override
    public int updateAddress(Address address) {
        return addressMapper.updateAddress(address);
    }

    @Override
    public void setDefault(Long id, Address address) {
        addressMapper.setNotDefault(address);
        addressMapper.setDefault(id);
    }

    @Override
    public Address getByAddId(Long id) {
        return addressMapper.selectByAddId(id);
    }

    @Override
    public Address getDefault(Long userId) {
        return addressMapper.getDefault(userId);
    }

    @Override
    public List<Map<String, Object>> getAlladdress(Long userId) {
        return addressMapper.getAlladdress(userId);
    }

}
