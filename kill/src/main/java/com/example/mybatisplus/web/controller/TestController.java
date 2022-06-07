package com.example.mybatisplus.web.controller;


import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.KillGoods;
import com.example.mybatisplus.service.KillGoodsService;
import com.example.mybatisplus.service.KillOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/kill/test")
public class TestController {
    @Autowired
    private KillGoodsService killGoodsService;
    @Autowired
    private KillOrdersService killOrdersService;

    @RequestMapping("/getTest")
    @ResponseBody
    public JsonResponse getGoodsInfo()throws Exception {
        Map<String,String> res=new HashMap<>();
        res.put("msg","商品库存不足！");
        return JsonResponse.success(res);
    }

    @RequestMapping("/ordersInfo")
    @ResponseBody
    public JsonResponse orderInfo() throws Exception {

        List<Map<String,Object>> orderInfo = killOrdersService.getOrderInfo();
        return JsonResponse.success(orderInfo);
    }

    @RequestMapping("/getKillOrdersInfo")
    @ResponseBody
    public JsonResponse goodsInfo() throws Exception {

        return JsonResponse.success(null);
    }

    @RequestMapping("/addOrder")
    @ResponseBody
    public JsonResponse add() throws Exception {

        return JsonResponse.success("goodsInfo");
    }

    @RequestMapping("/nullTest")
    @ResponseBody
    public JsonResponse nullTest() throws Exception {
        return JsonResponse.success("Hello world！");
    }
}
