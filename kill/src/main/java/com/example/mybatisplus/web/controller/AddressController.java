package com.example.mybatisplus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.AddressService;
import com.example.mybatisplus.model.domain.Address;


/**
 *
 *  前端控制器
 *
 *
 * @author hhf
 * @since 2022-05-25
 * @version v1.0
 */
@Controller
@RequestMapping("/kill/address")
public class AddressController {

    private final Logger logger = LoggerFactory.getLogger( AddressController.class );

    @Autowired
    private AddressService addressService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Address  address =  addressService.getById(id);
        return JsonResponse.success(address);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        addressService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateAddress(@PathVariable("id") Long  id,Address  address) throws Exception {
        address.setId(id);
        addressService.updateById(address);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Address
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Address  address) throws Exception {
        addressService.save(address);
        return JsonResponse.success(null);
    }
}

