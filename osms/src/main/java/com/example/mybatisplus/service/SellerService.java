package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Seller;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商家表 服务类
 * </p>
 *
 * @author tyl
 * @since 2022-03-09
 */
public interface SellerService extends IService<Seller> {

    void logoutById(Long id);
}
