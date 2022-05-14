package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Favorite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户商品收藏 Mapper 接口
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
@Repository
public interface FavoriteMapper extends BaseMapper<Favorite> {

    List<Map<String, Object>> getFavoriteProducts(Long user_id);
}
