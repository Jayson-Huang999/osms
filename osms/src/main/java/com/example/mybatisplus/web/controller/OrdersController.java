package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.TokenUtils;
import com.example.mybatisplus.mapper.OrdersMapper;
import com.example.mybatisplus.model.domain.Comments;
import com.example.mybatisplus.model.domain.Orders;
import com.example.mybatisplus.model.domain.Specification;
import com.example.mybatisplus.service.CommentsService;
import com.example.mybatisplus.service.OrdersService;
import com.example.mybatisplus.service.SpecificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
@RequestMapping("/api/orders")
public class OrdersController {

    private final Logger logger = LoggerFactory.getLogger( OrdersController.class );

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private CommentsService commentsService;

    //根据订单状态获取一个用户的所有订单
    @RequestMapping(value = "/getOrders", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getOrders(@RequestParam Long user_id,@RequestParam Long status,@RequestParam String token) throws Exception {
        System.err.println(TokenUtils.verify(token));
        if(TokenUtils.verify(token)) {
            List<Map<String, Object>> mapList;
            if (status == 0L) {
                mapList = ordersMapper.getOrdersByUserId(user_id);
            } else {
                mapList = ordersMapper.getOrders(user_id, status);
            }
            for (Map<String, Object> map : mapList) {
                QueryWrapper<Comments> wrapper = new QueryWrapper<>();
                wrapper.lambda().eq(Comments::getOrderId, map.get("order_id"));
                if (commentsService.list(wrapper).size() == 0)
                    map.put("hasComment", false);
                else
                    map.put("hasComment", true);
                map.put("order_id", String.valueOf(map.get("order_id")));
            }
            System.out.println(mapList);
            return JsonResponse.success(mapList);
        }
        else
            return JsonResponse.failure(null);
    }

    //修改订单地址
    @RequestMapping(value = "/updateOrdersAddress", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateOrdersAddress(@RequestParam Long address_id, @RequestParam Long order_id) throws Exception {
        Orders orders=new Orders().setId(order_id).setAddressId(address_id);
        ordersService.updateById(orders);
        return JsonResponse.success(null);
    }
    //取消订单
    @RequestMapping(value = "/cancelOrders", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse cancelOrders(@RequestParam Long order_id){
        Orders orders=new Orders().setId(order_id).setStatus(5L);
        ordersService.updateById(orders);
        return JsonResponse.success(null);
    }
    //获取一个订单的详细信息
    @RequestMapping(value = "/getOrdersDetail", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getOrdersDetail(@RequestParam Long order_id) {
        System.out.println(order_id);
        Map<String,Object> ordersDetail=ordersMapper.getOrdersDetail(order_id);
        System.out.println(ordersDetail);
        QueryWrapper<Specification> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(Specification::getProductId,ordersDetail.get("product_id"));
        List<Specification> specifications = specificationService.list(wrapper);

        ordersDetail.put("specs",specifications);
        System.out.println(ordersDetail);
        return JsonResponse.success(ordersDetail).setCode(0);
    }
    //购物车结算时提交订单(此操作前需要先在购物车去除选中内容)
    @RequestMapping(value = "/generateOrders", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse generateOrders(@RequestBody List<Orders> ordersList){
        for(Orders orders:ordersList) {
            orders.setCreatetime(LocalDateTime.now());
        }
        List<String> orderIdList=new ArrayList<>();
        ordersService.saveBatch(ordersList);
        for(Orders orders:ordersList){
            orderIdList.add(""+orders.getId());
        }
        return JsonResponse.success(orderIdList);
    }
    //更新订单状态
    @RequestMapping(value = "/updateOrdersStatus", method = RequestMethod.PUT)
    @ResponseBody
    @CrossOrigin
    public JsonResponse updateOrdersStatus(@RequestBody Orders orders){
        ordersService.updateById(orders);
        return JsonResponse.success(null);
    }
    //更新订单状态
    @RequestMapping(value = "/updateOrdersStatusByids", method = RequestMethod.PUT)
    @ResponseBody
    @CrossOrigin
    public JsonResponse updateOrdersStatusByids(@RequestBody List<String> orderIds){
        System.out.println(orderIds);
        ordersService.updateByIds(orderIds);
        return JsonResponse.success(null);
    }
    //商家修改订单
    @RequestMapping(value = "/changeOrder", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse changeOrder(@RequestBody Orders orders){
        System.out.println(orders);
        ordersService.updateById(orders);
        return JsonResponse.success(null).setCode(0).setMessage("修改成功！");
    }


    /**
     * 描述:商家根据自己的id查询自己店铺的订单信息
     *
     */
    @RequestMapping(value = "/selectOrdersBySellerIdAndStatus",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse selectOrdersBySellerIdAndStatus(@RequestBody Map<String,Object> myMap){
        System.out.println(myMap);
        List<Map<String,Object>> mapList;
        if (myMap.get("status").equals(0)){//查询商家所有的订单
            mapList = ordersMapper.selectOrdersBySellerId(myMap.get("sid"));
        }else{
            mapList = ordersService.selectOrdersBySellerIdAndStatus(myMap.get("sid"),myMap.get("status"));
        }
        for(Map<String,Object> map:mapList)
            map.put("id",String.valueOf(map.get("id")));
        System.out.println(mapList);
//        ResultList<Map<String,Object>> resultList = page(1, 1, map, true);
//        System.out.println(resultList);
        return JsonResponse.success(mapList);
    }
}

