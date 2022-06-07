package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.model.domain.KillGoods;
import com.example.mybatisplus.service.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.Orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 *
 *  前端控制器
 *
 *
 * @author hhf
 * @since 2022-05-31
 * @version v1.0
 */
@Controller
@RequestMapping("/kill/orders")
public class OrdersController {

    private final Logger logger = LoggerFactory.getLogger( OrdersController.class );

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private KillOrdersService killOrdersService;
    @Autowired
    private KillGoodsService killGoodsService;
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private ActivityService activityService;

    //秒杀商品结算时提交订单(此操作前需要先在购物车去除选中内容)
    @Transactional//开启事务
    @RequestMapping(value = "/generateKillOrders", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse generateKillOrders(@RequestBody List<Orders> ordersList){
        for(Orders orders:ordersList) {
            orders.setCreatetime(LocalDateTime.now());
        }

        List<String> orderIdList = new ArrayList<>();
        //下单操作
        ordersService.saveBatch(ordersList);
        for(Orders orders:ordersList){
            orderIdList.add(""+orders.getId());
            killGoodsService.decrementStock(orders.getGoodsId());
            killOrdersService.addOrder(orders.getId(),orders.getUserId(),orders.getGoodsId(),orders.getAddressId(),
                    orders.getTotalprice(),orders.getCreatetime());
            KillGoods goodsInfo = killGoodsService.getById(orders.getGoodsId());
            activityService.decStock(goodsInfo.getActivityId());
            specificationService.decStock(orders.getSpecificationId());
        }

        return JsonResponse.success(orderIdList);
    }

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Orders  orders =  ordersService.getById(id);
        return JsonResponse.success(orders);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        ordersService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateOrders(@PathVariable("id") Long  id,Orders  orders) throws Exception {
        orders.setId(id);
        ordersService.updateById(orders);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Orders
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Orders  orders) throws Exception {
        ordersService.save(orders);
        return JsonResponse.success(null);
    }
}

