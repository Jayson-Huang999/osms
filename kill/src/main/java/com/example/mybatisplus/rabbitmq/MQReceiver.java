package com.example.mybatisplus.rabbitmq;

import com.example.mybatisplus.common.utls.GsonUtil;
import com.example.mybatisplus.model.domain.*;
import com.example.mybatisplus.service.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MQReceiver {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private KillGoodsService killGoodsService;
    @Autowired
    private KillOrdersService killOrdersService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private ProductService productService;
    //下单操作
    @Transactional//开启事务
    @RabbitListener(queues = "killQueue")
    @Async
    public void receive(String msg){
        //System.out.println("收到的消息"+msg.toString());
        //预减库存
        OrderMessage orderMessage= GsonUtil.gsonToBean(msg,OrderMessage.class);
        Long userId=orderMessage.getUserId();
        Long goodsId=orderMessage.getGoodsId();

        //生成订单信息
        Map<String,Object> killOrders=new ConcurrentHashMap<>();
        KillGoods goodsInfo=killGoodsService.getById(goodsId);
        Activity activity=activityService.getById(goodsInfo.getActivityId());
        Specification specification=specificationService.getById(goodsInfo.getSpecificationId());
        Product product=productService.getById(activity.getProductId());
        killOrders.put("specification_id",goodsInfo.getSpecificationId());
        killOrders.put("price",goodsInfo.getPrice());
        killOrders.put("product_name",product.getName());
        killOrders.put("specification_name",specification.getName());
        killOrders.put("num",1);
        killOrders.put("img",specification.getImg());
        killOrders.put("size",specification.getSize());
        killOrders.put("preferentiallevel",activity.getPreferentiallevel());
        killOrders.put("activity_id",goodsInfo.getActivityId());
        killOrders.put("goods_id",goodsId);
        killOrders.put("user_id",userId);
        //将订单存到redis中
        redisTemplate.opsForValue().set("order:"+userId+":"+goodsId,1);
        redisTemplate.opsForValue().set("killOrderInfo:"+goodsId+":"+userId,killOrders);
    }
}
