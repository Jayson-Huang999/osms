package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.mapper.FavoriteMapper;
import com.example.mybatisplus.model.domain.Favorite;
import com.example.mybatisplus.service.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.example.mybatisplus.model.domain.PageUtil.page;


/**
 *
 *  前端控制器
 *
 *
 * @author lxp
 * @since 2022-03-02
 * @version v1.0
 */
@Controller
@RequestMapping("/api/favorite")
public class FavoriteController {

    private final Logger logger = LoggerFactory.getLogger( FavoriteController.class );

    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private FavoriteMapper favoriteMapper;

    //添加商品到收藏夹
    @RequestMapping(value = "/addToFavorite", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addToFavorite(@RequestBody Favorite favorite){
        favoriteService.save(favorite);
        return JsonResponse.success(null);
    }
    //从收藏夹中移除商品
    @RequestMapping(value = "/removeFromFavorite", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse removeFromFavorite(@RequestParam Long favorite_id){
        favoriteService.removeById(favorite_id);
        return JsonResponse.success(null);
    }
    //获取收藏夹中商品
    @RequestMapping(value = "/getFavoriteProducts", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getFavoriteProducts(@RequestParam("id") Long user_id,Integer start,Integer limit){
        return JsonResponse.success(page(start,limit,favoriteMapper.getFavoriteProducts(user_id),true));
    }
}

