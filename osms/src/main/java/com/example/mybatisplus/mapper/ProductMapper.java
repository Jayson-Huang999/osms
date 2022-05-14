package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.domain.Product;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息表 Mapper 接口
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
@Repository
public interface ProductMapper extends BaseMapper<Product> {

    List<Map<String, Object>> selectBySellerId(Long id);

    Product getProductInfoByID(Long id);

    @MapKey("id")
    List<Map<String,Object>> getProductInfoByCategoryID(@Param("id") Long id);

    @MapKey("id")
    List<Map<String, Object>> getProductDetailByID(Long id);

    @MapKey("id")
    List<Map<String, Object>> searchProductByKeyWord(String keyword);

    @MapKey("id")
    List<Map<String, Object>> getProductListByCidAndSid(Map<String, Object> data);

    Long hasStored(Long userId,Long pId);

    void addStore(Long userid, Long pid);

    void cancelStore(Long userid, Long pid);
    @MapKey("id")
    List<Map<String, Object>> getfavoriteGoodsList();
    @MapKey("id")
    List<Map<String, Object>> getNewGoodsList();
    @MapKey("id")
    List<Map<String, Object>> getGoodsListBybid(Long id);
    @MapKey("id")
    List<Map<String, Object>> getAllProductInfo();
    @MapKey("id")
    Map<String,Object> getProductStock(Long id);
}
