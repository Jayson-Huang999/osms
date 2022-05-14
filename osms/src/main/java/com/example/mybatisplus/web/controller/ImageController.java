package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.Image;
import com.example.mybatisplus.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
@RequestMapping("/api/image")
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
        Image image =  imageService.getById(id);
        return JsonResponse.success(image);
    }

    /**
    * 描述：根据Id删除
    *
    */
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
//        imageService.removeById(id);
//        return JsonResponse.success(null);
//    }
    @RequestMapping(value = "/deleteImageByID", method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public JsonResponse deleteImageByID(@RequestParam Long id){
        if(imageService.removeById(id)){
            return JsonResponse.success(id).setCode(0).setMessage("删除成功!");
        }else{
            return JsonResponse.success(null).setCode(10000).setMessage("删除失败！");
        }
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateImage(Image image) throws Exception {
        imageService.updateById(image);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Image
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Image image) throws Exception {
        imageService.save(image);
        return JsonResponse.success(null);
    }
    /**
     * 描述：根据商品id添加商品图片
     *
     */
    @RequestMapping(value = "/addImageByProductID", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse addImageByProductID(@RequestBody Image image) throws Exception {
        System.out.println(image);
        imageService.save(image);
        return JsonResponse.success(image).setCode(0).setMessage("添加商品图片成功！");
    }
}

