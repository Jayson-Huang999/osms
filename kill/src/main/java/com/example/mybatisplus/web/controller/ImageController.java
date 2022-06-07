package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.ImageService;
import com.example.mybatisplus.model.domain.Image;


/**
 *
 *  前端控制器
 *
 *
 * @author hhf
 * @since 2022-05-31
 * @version v1.0
 */
@Controller
@RequestMapping("/kill/image")
public class ImageController {

    private final Logger logger = LoggerFactory.getLogger( ImageController.class );

    @Autowired
    private ImageService imageService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Image  image =  imageService.getById(id);
        return JsonResponse.success(image);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        imageService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateImage(@PathVariable("id") Long  id,Image  image) throws Exception {
        image.setId(id);
        imageService.updateById(image);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Image
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Image  image) throws Exception {
        imageService.save(image);
        return JsonResponse.success(null);
    }
}

