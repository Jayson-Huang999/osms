package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.TokenUtils;
import com.example.mybatisplus.mapper.CartProductMapper;
import com.example.mybatisplus.model.domain.CartProduct;
import com.example.mybatisplus.service.CartProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 前端控制器
 *
 * @author lxp
 * @version v1.0
 * @since 2022-03-02
 */
@Controller
@RequestMapping("/api/cartProduct")
public class CartProductController {

    private final Logger logger = LoggerFactory.getLogger(CartProductController.class);

    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private CartProductMapper cartProductMapper;


    //添加商品到购物车
    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse addToCart(@RequestBody CartProduct cart) {
//        System.err.println(map);
//        System.err.println(map.get("data"));
//        CartProduct cart=(CartProduct)map.get("data");
        QueryWrapper<CartProduct> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CartProduct::getSpecificationId, cart.getSpecificationId()).eq(CartProduct::getUserId, cart.getUserId());
        List<CartProduct> cartProductList = cartProductService.list(wrapper);
        if (cartProductList.size() > 0)
            cartProductService.updateById(new CartProduct().setId(cartProductList.get(0).getId()).setQuantity(cartProductList.get(0).getQuantity() + cart.getQuantity()));
        else
            cartProductService.save(cart);
        return JsonResponse.success(cart).setCode(0).setStatus(true);
    }

    //从购物车中移除商品
    @RequestMapping(value = "/removeFromCart", method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public JsonResponse removeFromCart(@RequestBody List<Long> idlist) {
        cartProductService.removeByIds(idlist);
        return JsonResponse.success(null);
    }

    //获取购物车内的商品
    @RequestMapping(value = "/getCartProducts", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getCartProducts(@RequestParam Long user_id,@RequestParam String token) {
        System.err.println(TokenUtils.verify(token));
        if(TokenUtils.verify(token))
            return JsonResponse.success(cartProductMapper.getCartProducts(user_id)).setStatus(true);
        else
            return JsonResponse.failure(null);
    }

    //修改购物车商品的数量
    @RequestMapping(value = "/updateCart", method = RequestMethod.PUT)
    @ResponseBody
    @CrossOrigin
    public JsonResponse updateCart(@RequestBody CartProduct cart) {
        cartProductService.updateById(cart);
        return JsonResponse.success(null).setCode(0);
    }

}
