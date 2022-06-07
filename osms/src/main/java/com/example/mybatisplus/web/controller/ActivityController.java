package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.mapper.ActivityMapper;
import com.example.mybatisplus.model.Entity.SpecsInfo;
import com.example.mybatisplus.model.domain.Activity;
import com.example.mybatisplus.model.Entity.ActivityInfo;
import com.example.mybatisplus.model.domain.KillGoods;
import com.example.mybatisplus.model.domain.Product;
import com.example.mybatisplus.service.ActivityService;
import com.example.mybatisplus.service.KillGoodsService;
import com.example.mybatisplus.service.ProductService;
import org.redisson.api.RSemaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 *
 *  前端控制器
 *
 *
 * @author tyl
 * @since 2022-03-09
 * @version v1.0
 */
@CrossOrigin
@Controller
@RequestMapping("/api/activity")
public class ActivityController {

    private final Logger logger = LoggerFactory.getLogger( ActivityController.class );

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private KillGoodsService killGoodsService;



    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Activity activity = activityService.getById(id);
        return JsonResponse.success(activity);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        activityService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateActivity(Activity activity) throws Exception {
        activityService.updateById(activity);
        return JsonResponse.success(null);
    }


    /**
     * 描述:添加Activity
     */
    @RequestMapping(value = "/addActivity", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addActivity(@RequestBody ActivityInfo activityInfo) throws Exception {
        //System.out.println(activityInfo);
        //activityService.save(activityInfo.getActivity());
        List<SpecsInfo> specsInfos=activityInfo.getSpecsInfo();
        Long preferentiallevel=activityInfo.getPreferentiallevel();
        int number=0;
        for(SpecsInfo item:specsInfos){
            if(item.getChecked()!=null){
                number+=item.getNum();
            }
        }
        Activity activity=new Activity(activityInfo.getProductId(),preferentiallevel,
                activityInfo.getStarttime(),activityInfo.getEndtime(),number,number,activityInfo.getStatus());
        activityMapper.insert(activity);
        Long activityId=activity.getId();
        System.out.println("插入数据的id:"+ activityId.toString());
        for(SpecsInfo item:specsInfos) {
            if (item.getChecked()!=null) {
                BigDecimal price=(BigDecimal) item.getPrice().multiply(new BigDecimal((float)preferentiallevel/100));
                System.out.println(price);
                killGoodsService.save(new KillGoods(item.getProductId(), (long) activityId, item.getId(), item.getNum(), price));
            }
        }
        return JsonResponse.success(null).setCode(0).setMessage("活动添加成功！");
    }

    //查询所有活动商品的信息
    @RequestMapping(value = "/getActivityProducts", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getActivityProducts() {
       //商品信息加库存
        activityService.updateKillGoodsInfo();
        return JsonResponse.success(activityMapper.getActivityProducts());

    }

    //管理员审核活动
    @RequestMapping(value = "/updateActivityStatus", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateActivityStatus(@RequestParam Long activity_id) {
        activityService.updateById(new Activity().setId(activity_id).setStatus(1L));
        return JsonResponse.success(null);
    }
    //更新活动商品的库存
    @RequestMapping(value = "/updateActivityRemain", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateActivityRemain(@RequestBody List<Map<String,Object>> maplist){
        for(Map<String,Object> map:maplist){
            activityMapper.updateActivityRemain(Long.valueOf(map.get("activity_id").toString()),(Integer) map.get("num"));
        }
        return JsonResponse.success(null);
    }

    /**
     * 描述:根据商家的status查找信息
     */
    @RequestMapping(value = "/getActivityByStatus", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getActivityByStatus(@RequestParam Long status) {
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Activity::getStatus, status);
        wrapper.orderByDesc("starttime");
        List<Activity> activities = activityService.list(wrapper);
        for(Activity activity:activities){
            QueryWrapper<Product>wrapper1=new QueryWrapper<>();
            wrapper1.lambda().eq(Product::getId,activity.getProductId());
            Product product=productService.getOne(wrapper1);
            activity.setProductName(product.getName());
        }
        System.out.println(activities);
        return JsonResponse.success(activities);
    }

    /**
     * 描述：模糊查询用户信息
     */
    @RequestMapping(value = "/getSearchActivity", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getSearchActivity(@RequestBody Map<String, Object> map) {
        System.out.println(map);
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.and(
                wrap -> wrap
                        .like("name", map.get("keyword")).or()
                        .like("phone", map.get("keyword")).or()
                        .like("account", map.get("keyword")).or()
                        .like("location", map.get("keyword"))
        );

        wrapper.lambda().eq(Activity::getStatus, map.get("status"));
        wrapper.orderByDesc("starttime");

        List<Activity> list = activityService.list(wrapper);
        System.out.println(list);
        return JsonResponse.success(list).setCode(0);
    }

    /**
     * 描述：根据Id注销违规商家账户
     */
    @RequestMapping(value = "/changeActivityByID", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse changeActivityByID(@RequestBody Map<String, Object> map) throws Exception {
        System.out.println(map);
        UpdateWrapper<Activity> wrapper = new UpdateWrapper<>();
        if (map.get("flag").equals(1)) {
            wrapper.set("status", 1).in("id", map.get("id"));
        } else if (map.get("flag").equals(2)) {
            wrapper.set("status", 2).in("id", map.get("id"));
        } else if (map.get("flag").equals(3)) {
            wrapper.set("status", 2).in("id", map.get("id"));
        } else {
            wrapper.set("status", 0).in("id", map.get("id"));
        }
        activityService.update(wrapper);
        return JsonResponse.success(null).setCode(0);
    }
}

