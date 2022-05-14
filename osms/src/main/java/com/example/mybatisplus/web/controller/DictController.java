package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.Dict;
import com.example.mybatisplus.service.DictService;
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
@RequestMapping("/api/dict")
public class DictController {

    private final Logger logger = LoggerFactory.getLogger( DictController.class );

    @Autowired
    private DictService dictService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Dict dict =  dictService.getById(id);
        return JsonResponse.success(dict);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        dictService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateDict(Dict dict) throws Exception {
        dictService.updateById(dict);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Dict
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Dict dict) throws Exception {
        dictService.save(dict);
        return JsonResponse.success(null);
    }
}

