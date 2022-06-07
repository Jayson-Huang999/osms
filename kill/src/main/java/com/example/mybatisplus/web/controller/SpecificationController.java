package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.SpecificationService;
import com.example.mybatisplus.model.domain.Specification;


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
@RequestMapping("/kill/specification")
public class SpecificationController {

    private final Logger logger = LoggerFactory.getLogger( SpecificationController.class );

    @Autowired
    private SpecificationService specificationService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Specification  specification =  specificationService.getById(id);
        return JsonResponse.success(specification);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        specificationService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateSpecification(@PathVariable("id") Long  id,Specification  specification) throws Exception {
        specification.setId(id);
        specificationService.updateById(specification);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Specification
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Specification  specification) throws Exception {
        specificationService.save(specification);
        return JsonResponse.success(null);
    }
}

