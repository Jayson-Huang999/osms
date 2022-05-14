package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.Preferentiallevel;
import com.example.mybatisplus.service.PreferentiallevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


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
@RequestMapping("/api/preferentiallevel")
public class PreferentiallevelController {

    private final Logger logger = LoggerFactory.getLogger( PreferentiallevelController.class );

    @Autowired
    private PreferentiallevelService preferentiallevelService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Preferentiallevel preferentiallevel =  preferentiallevelService.getById(id);
        return JsonResponse.success(preferentiallevel);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        preferentiallevelService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updatePreferentiallevel(Preferentiallevel preferentiallevel) throws Exception {
        preferentiallevelService.updateById(preferentiallevel);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Preferentiallevel
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Preferentiallevel preferentiallevel) throws Exception {
        preferentiallevelService.save(preferentiallevel);
        return JsonResponse.success(null);
    }
}