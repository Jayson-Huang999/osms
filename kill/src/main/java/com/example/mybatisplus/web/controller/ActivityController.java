package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.common.utls.GsonUtil;
import com.example.mybatisplus.common.utls.SnowflakeIdUtil;
import com.example.mybatisplus.model.domain.OrderMessage;
import com.example.mybatisplus.rabbitmq.MQSender;
import com.example.mybatisplus.service.KillGoodsService;
import com.example.mybatisplus.service.KillOrdersService;
import com.google.gson.Gson;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ActivityService;
import com.example.mybatisplus.model.domain.Activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 *
 *  前端控制器
 *
 *
 * @author hhf
 * @since 2022-05-24
 * @version v1.0
 */
@Controller
@RequestMapping("/kill/activity")
public class ActivityController implements InitializingBean {
/*  1.还要实现的功能:发起活动请求的时候加上商品规格及其数量，完善对应的前端，活动加一个上架状态
    2.商品展示加一个秒杀界面
    3.秒杀前端在点击抢购按钮时显示排队中，前端轮询查询结果，抢单成功后引导至信息修改界面，进行付款等流程，进行接口压测
    4.取消订单则数据库加一，看原来的怎么处理，如果可以直接调用原来的接口
    5.优化：用户点击成功后不可重复点击、活动未开始前不可点击、点击后要有验证码操作、秒杀倒计时、商品详情页面静态化以及秒杀静态化安全优化
    6.登录功能完善
* */
    private final Logger logger = LoggerFactory.getLogger( ActivityController.class );

    @Autowired
    private ActivityService activityService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MQSender mqSender;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private KillGoodsService killGoodsService;
    @Autowired
    private KillOrdersService killOrdersService;

    private SnowflakeIdUtil snowflakeIdUtil=new SnowflakeIdUtil(0,0);


    private Map<Long,Boolean> EmptyStockMap=new ConcurrentHashMap<>();

    /**
     * 描述:初始化执行商品、秒杀订单加到redis，
     *
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //商品信息加库存
        List<Map<String,Object>> goodsInfo = killGoodsService.getGoodsInfo();
        if(goodsInfo.isEmpty()){
            return ;
        }
        for(Map<String,Object> map:goodsInfo){
            //获取分布式锁，使用库存作为信号量
            RSemaphore semaphore = redissonClient.getSemaphore("kill_goods"+map.get("id"));
            semaphore.trySetPermits((int)map.get("stock"));//设置信号量：能秒杀商品数量
            EmptyStockMap.put((Long) map.get("id"), false);//用map做内存标记
        }
        //订单信息加库存
        List<Map<String,Object>> orderInfo = killOrdersService.getOrderInfo();
        for(Map<String,Object> map:orderInfo){
            redisTemplate.opsForValue().set("order:"+map.get("userId")+":"+map.get("goodsId"),1);
        }
    }
    /**
     * 描述:查询秒杀订单信息
     */
    @RequestMapping("/queryKillRes")
    @ResponseBody
    public JsonResponse queryKillRes(@RequestBody Map<String,Long> params)throws Exception {
        return JsonResponse.success(redisTemplate.opsForValue().get("killOrderInfo:"+params.get("goodsId")+":"+params.get("userId")));
    }

    /**
     * 描述：秒杀请求
     *问题：超卖，QPS:800
     * 1.库存更新的时候判断是否大于0，大于0更新，不大于0拒绝更新。
     * 2.通过唯一索引的方式解决重复抢购的同时请求问题
     * 3.把订单和商品信息存到redis中,redis预减库存
     * 4.订单异步操作，返回排队中，
     * 5.客户端轮询，判断是否秒杀成功
     * 8.解决：redis缓存不一致，（
     *      延时双删：
     *      1.删除redis
     *      2.更新数据库
     *      3.延时500毫秒
     *      4.删除redis）
     */
    @RequestMapping("/setKill")
    @ResponseBody
    public JsonResponse kill(@RequestBody Map<String,Long> params)throws Exception {
    //public JsonResponse kill(@RequestParam("goodsId") Long goodsId)throws Exception {
        //Long userId=snowflakeIdUtil.nextId();
        //System.out.println(goodsId+userId);
        Map<String,String> res=new ConcurrentHashMap<>();
        Long goodsId=params.get("goodsId");
        Long userId=params.get("userId");
        System.out.println("goodsId:"+goodsId+"userId"+userId);
        if(userId==null){
            res.put("msg","请登录！");
            return JsonResponse.success(res);
        }
        //内存标记，减少Redis的访问
        if (EmptyStockMap.get(goodsId)==null) {
            EmptyStockMap.put(goodsId,false);
        } else if(EmptyStockMap.get(goodsId)){
            res.put("msg","商品库存不足！");
            return JsonResponse.success(res);
        }
        //防止重复购买
        if(redisTemplate.opsForValue().get("order:"+userId+":"+goodsId)!=null) {
            res.put("msg","您已经抢购过该商品！");
            return JsonResponse.success(res);
        }
        //获取信号量
        RSemaphore semaphore = redissonClient.getSemaphore("kill_goods" + goodsId);
        //num为可以单个用户可以秒杀的商品，
        int num = 1;
        //tryAcquire非阻塞获取信号，会减一，预减库存
        //System.out.println("当前信号量值为："+semaphore.availablePermits());
        boolean b = semaphore.tryAcquire(num, 100, TimeUnit.MILLISECONDS);
        //System.out.println("当前信号量值为："+semaphore.availablePermits());
        if(!b){
            EmptyStockMap.put(goodsId, true);
            res.put("msg","商品库存不足！");
            return JsonResponse.success(res);
        }
        //下单异步
        OrderMessage msg=new OrderMessage(userId,goodsId);
        mqSender.send(GsonUtil.objectToJson(msg));
        res.put("msg","抢购商品排队中，请稍候...");
        return JsonResponse.success(res);
    }

    /**
     * 描述：抢购商品失败
     *
     */
    @RequestMapping("/falseToKill")
    @ResponseBody
    public JsonResponse falseToKill(@RequestBody Map<String,Long> params)throws Exception {
        Long goodsId=params.get("goodsId");
        Long userId=params.get("userId");
        //内存标记设置为真
        EmptyStockMap.put(goodsId,false);
        //获取信号量
        RSemaphore semaphore = redissonClient.getSemaphore("kill_goods" + goodsId);
        semaphore.release();
        //删除redis的订单
        redisTemplate.delete("order:"+userId+":"+goodsId);
        redisTemplate.delete("killOrderInfo:"+goodsId+":"+userId);
        return JsonResponse.success(null);
    }

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Activity  activity =  activityService.getById(id);
        return JsonResponse.success(activity);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        activityService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateActivity(@PathVariable("id") Long  id,Activity  activity) throws Exception {
        activity.setId(id);
        activityService.updateById(activity);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Activity
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Activity  activity) throws Exception {
        activityService.save(activity);
        return JsonResponse.success(null);
    }
}

