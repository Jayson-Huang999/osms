package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.mapper.SpecificationMapper;
import com.example.mybatisplus.model.domain.Specification;
import com.example.mybatisplus.service.SpecificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 前端控制器
 *
 * @author zp
 * @version v1.0
 * @since 2022-03-03
 */
@CrossOrigin
@Controller
@RequestMapping("/api/specification")
public class SpecificationController {

    private final Logger logger = LoggerFactory.getLogger(SpecificationController.class);

    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private SpecificationMapper specificationMapper;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Specification specification = specificationService.getById(id);
        return JsonResponse.success(specification);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        specificationService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateSpecification(Specification specification) throws Exception {
        specificationService.updateById(specification);
        return JsonResponse.success(null);
    }

    /**
     * 描述:创建Specification
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Specification specification) throws Exception {
        specificationService.save(specification);
        return JsonResponse.success(null);
    }


    /**
     * 描述:商家通过商品id对商品规格进行修改
     */
//    @RequestMapping("/updateSpe")
//    @ResponseBody
//    public JsonResponse updateByPid(@RequestBody JSONObject jsonObject){
//        specificationService.updateByPid(jsonObject.get("id"),jsonObject.get("size"),jsonObject.get("price"),jsonObject.get("stock"));
//        return JsonResponse.success(null);
//    }
    @RequestMapping(value = "/updateSpec", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateSpec(@RequestBody Specification specification) {
        System.out.println(specification);
        specificationService.updateById(specification);
        return JsonResponse.success(specification).setCode(0).setStatus(true);
    }

    /**
     * 说明：添加商品规格
     */
    @RequestMapping(value = "/addSpecByProductID", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addSpecByProductID(@RequestBody Specification specification) throws Exception {
        System.out.println(specification);
        specificationService.save(specification);
        return JsonResponse.success(specification).setCode(0).setMessage("添加成功！");
    }

    /**
     * 说明：根据id删除商品规格
     */
    @RequestMapping(value = "/deleteSpecByID", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteSpecByID(@RequestParam Long id) {
        if (specificationService.removeById(id)) {
            return JsonResponse.success(id).setCode(0).setMessage("删除成功!");
        } else {
            return JsonResponse.success(null).setCode(10000).setMessage("删除失败！");
        }
    }


    @RequestMapping(value = "/getTotalStockByPid", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getTotalStockByPid(@RequestParam Long id) throws Exception {
        int totalnum = specificationMapper.getTotalStockByPid(id);
        System.out.println("TotalNum: "+totalnum);
        return JsonResponse.success(totalnum).setCode(0);
    }
    @RequestMapping(value = "/updateSpecificationStock", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateSpecificationStock(@RequestBody List<Map<String,Object>> maplist) throws Exception {
        for(Map<String,Object> map:maplist)
            specificationMapper.updateSpecificationStock(Long.valueOf(map.get("specification_id").toString()),(Integer)(map.get("num")));
        return JsonResponse.success(null);
    }
}
