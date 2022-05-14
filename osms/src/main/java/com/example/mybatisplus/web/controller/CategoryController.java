package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.Category;
import com.example.mybatisplus.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
@RequestMapping("/api/category")
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger( CategoryController.class );

    @Autowired
    private CategoryService categoryService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Category  category =  categoryService.getById(id);
        return JsonResponse.success(category);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        categoryService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateCategory(Category  category) throws Exception {
        categoryService.updateById(category);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Category
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Category  category) throws Exception {
        categoryService.save(category);
        return JsonResponse.success(null);
    }

    /**
     * 描述:获取所有的商品种类数据
     *
     */
    @RequestMapping(value = "/getAllCategories", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getAllCategories(){
        List<Category> list=categoryService.list();
        System.out.println(list);
        return JsonResponse.success(list).setCode(0);
    }
    /**
     * 说明：添加商品种类
     */
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addCategory(@RequestBody Category category)throws Exception{
        System.out.println(category);
        categoryService.save(category);
        return JsonResponse.success(category).setCode(0);
    }
}

