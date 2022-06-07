package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.KillOrdersService;
import com.example.mybatisplus.model.domain.KillOrders;


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
@RequestMapping("/api/killOrders")
public class KillOrdersController {

    private final Logger logger = LoggerFactory.getLogger( KillOrdersController.class );

    @Autowired
    private KillOrdersService killOrdersService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        KillOrders  killOrders =  killOrdersService.getById(id);
        return JsonResponse.success(killOrders);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        killOrdersService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateKillOrders(KillOrders  killOrders) throws Exception {
        killOrdersService.updateById(killOrders);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建KillOrders
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(KillOrders  killOrders) throws Exception {
        killOrdersService.save(killOrders);
        return JsonResponse.success(null);
    }
}

