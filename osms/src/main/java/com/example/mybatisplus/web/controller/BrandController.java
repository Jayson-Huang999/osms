package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.Brand;
import com.example.mybatisplus.model.domain.Category;
import com.example.mybatisplus.service.BrandService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.BrandService;
import com.example.mybatisplus.model.domain.Brand;
import java.util.List;

import java.util.List;


/**
 *
 *  前端控制器
 *
 *
 * @author tyl
 * @since 2022-03-09
 * @version v1.0
 */
@Controller
@RequestMapping("/api/brand")
public class BrandController {

    private final Logger logger = LoggerFactory.getLogger( BrandController.class );

    @Autowired
    private BrandService brandService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Brand  brand =  brandService.getById(id);
        return JsonResponse.success(brand);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        brandService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateBrand(Brand  brand) throws Exception {
        brandService.updateById(brand);
        return JsonResponse.success(null);

    }


    /**
    * 描述:创建Brand
    *
    */
    /**
     * 说明：添加商品品牌
     */
    @RequestMapping(value = "/addBrand", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse addBrand(@RequestBody Brand brand)throws Exception{
        System.out.println(brand);
        brandService.save(brand);
        return JsonResponse.success(brand).setCode(0);
    }

    /**
     * 描述:获取所有的商品品牌数据
     *
     */
    @RequestMapping(value = "/getAllBrands", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getAllBrands(){
        List<Brand> list=brandService.list();
        list.add(0, new Brand().setId( Long.valueOf(0)).setName("全部"));
        System.out.println(list);
        return JsonResponse.success(list).setCode(0);
    }
    /**
     * 描述:根据目录获取所有的商品品牌数据
     *
     */
    @RequestMapping(value = "/getBrandByCid", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getBrandByCid(Long id){
        QueryWrapper<Brand>wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(Brand::getCategoryId,id);
        List<Brand> list=brandService.list(wrapper);
        list.add(0, new Brand().setId( Long.valueOf(0)).setName("全部"));
        System.out.println(list);
        return JsonResponse.success(list).setCode(0);
    }
}

