package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.KillGoodsService;
import com.example.mybatisplus.model.domain.KillGoods;


/**
 *
 *  前端控制器
 *
 *
 * @author zp
 * @since 2022-05-30
 * @version v1.0
 */
@Controller
@RequestMapping("/api/killGoods")
public class KillGoodsController {

    private final Logger logger = LoggerFactory.getLogger( KillGoodsController.class );

    @Autowired
    private KillGoodsService killGoodsService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        KillGoods  killGoods =  killGoodsService.getById(id);
        return JsonResponse.success(killGoods);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        killGoodsService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateKillGoods(KillGoods  killGoods) throws Exception {
        killGoodsService.updateById(killGoods);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建KillGoods
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(KillGoods  killGoods) throws Exception {
        killGoodsService.save(killGoods);
        return JsonResponse.success(null);
    }
}

