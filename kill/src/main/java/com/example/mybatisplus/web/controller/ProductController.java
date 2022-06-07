package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.mapper.ProductMapper;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ProductService;
import com.example.mybatisplus.model.domain.Product;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


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
@RequestMapping("/kill/product")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger( ProductController.class );

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedissonClient redissonClient;
    //获取秒杀商品库存
    @RequestMapping(value = "/getKillGoodsStock", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getKillGoodsStock(@RequestBody Map<String,Long> params){
        Map<String,Object> res = new ConcurrentHashMap<>();
        RSemaphore semaphore = redissonClient.getSemaphore("kill_goods" + params.get("goodsId"));
        if(semaphore!=null){
            res.put("stock",semaphore.availablePermits());
        } else {
            res.put("stock",null);
        }
        return JsonResponse.success(res);
    }
    /**
     * 描述:获取所有的秒杀商品数据
     *
     */
    @RequestMapping(value = "/getKillProductInfoByID", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getKillProductInfoByID(@RequestParam Long id){
        Product product=productMapper.getKillProductInfoByID(id);
        System.out.println(product);
        return JsonResponse.success(product).setCode(0);
    }

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Product  product =  productService.getById(id);
        return JsonResponse.success(product);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        productService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateProduct(@PathVariable("id") Long  id,Product  product) throws Exception {
        product.setId(id);
        productService.updateById(product);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Product
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Product  product) throws Exception {
        productService.save(product);
        return JsonResponse.success(null);
    }
}

