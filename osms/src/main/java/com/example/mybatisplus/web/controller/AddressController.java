package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.TokenUtils;
import com.example.mybatisplus.model.domain.Address;
import com.example.mybatisplus.service.AddressService;
//import jdk.nashorn.internal.parser.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 *
 *  前端控制器
 *
 *
 * @author lgl
 * @since 2022-03-01
 * @version v1.0
 */
@CrossOrigin
@Controller
@RequestMapping("/api/address")
public class AddressController {

    private final Logger logger = LoggerFactory.getLogger( AddressController.class );

    @Autowired
    private AddressService addressService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/address", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(Long id)throws Exception {
        Address  address =  addressService.getByAddId(id);
        System.out.println(address);
        return JsonResponse.success(address);
    }

    /**
     * 描述：根据UserId 查询
     *
     */
    @RequestMapping(value = "/userId", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getByUserId(Long userId)throws Exception {
        List<Address> list =  addressService.getByUserId(userId);
        return JsonResponse.success(list);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable Long id) throws Exception {
        System.out.println(id);
        addressService.removeById(id);
        return JsonResponse.success(null);
    }

    //添加收货地址
    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addAddress( Address form){
        System.out.println(form);
        addressService.save(form);
        return JsonResponse.success(addressService.getAlladdress(form.getUserId()));
    }
    //删除收货地址
    @RequestMapping(value = "/removeAddress", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse removeAddress(@RequestBody List<Long> idList){
        addressService.removeByIds(idList);
        return JsonResponse.success(null);
    }
    //获取一个用户的所有收货地址
    @RequestMapping(value = "/getAddresses", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAddresses(@RequestParam Long user_id,@RequestParam String token){
        System.err.println(TokenUtils.verify(token));
        if(TokenUtils.verify(token)) {
            QueryWrapper<Address> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Address::getUserId, user_id);
            return JsonResponse.success(addressService.list(wrapper));
        }
        else
            return JsonResponse.failure(null);
    }

//    /**
//     * 描述：增加地址信息
//     *
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonResponse addAddress(Long userId,String receivername , String receiverphone, String receiverprovince,
//                                   String receivercity, String receiverdistrict, String receiveraddress)throws Exception {
//        Address address =  new Address();
//        address.setUserId(userId);
//        address.setReceivername(receivername);
//        address.setReceiverphone(receiverphone);
//        address.setReceiverprovince(receiverprovince);
//        address.setReceivercity(receivercity);
//        address.setReceiverdistrict(receiverdistrict);
//        address.setReceiveraddress(receiveraddress);
//        addressService.addAddress(address);
//        return JsonResponse.success(null);
//    }

    /**
     * 描述：修改地址信息
     *
     */

    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateAddress( Address address){
        addressService.updateAddress(address);
        return JsonResponse.success(addressService.getAlladdress(address.getUserId()));
    }

//        @RequestMapping(value = "/update", method = RequestMethod.POST)
//        @ResponseBody
//        public JsonResponse updateAddress(Long id, String receivername , String receiverphone, String receiverprovince,
//                String receivercity, String receiverdistrict,String receiveraddress)throws Exception {
//            Address address =  new Address();
//            address.setId(id);
//            address.setReceivername(receivername);
//            address.setReceiverphone(receiverphone);
//            address.setReceiverprovince(receiverprovince);
//            address.setReceivercity(receivercity);
//            address.setReceiverdistrict(receiverdistrict);
//            address.setReceiveraddress(receiveraddress);
//            addressService.updateAddress(address);
//            return JsonResponse.success(address);
//        }
    /**
     * 描述：设为默认地址
     *
     */
    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse setDefault (Long id, Long userId) {
        Address address = addressService.getDefault(userId);
        addressService.setDefault(id, address);
        return JsonResponse.success(null);
    }

    /**
     * 描述：获取默认地址
     *
     */
    @RequestMapping(value = "/getDefault", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getDefault (Long userId) {
        Address address = addressService.getDefault(userId);
        return JsonResponse.success(address);
    }

    /**
    * 描述：根据Id 更新
    *
    */
//    @RequestMapping(value = "", method = RequestMethod.PUT)
//    @ResponseBody
//    public JsonResponse updateAddress(Address  address) throws Exception {
//        addressService.updateById(address);
//        return JsonResponse.success(null);
//    }


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
    /**
     * 描述:创建Address
     *
     */
    @RequestMapping(value = "/getAlladdress", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getAlladdress (Long id) {
//        System.out.println("userId"+id);
        List<Map<String,Object>> list = addressService.getAlladdress(id);
        return JsonResponse.success(list);
    }
    /**
     * 描述:修改Address
     *
     */
//    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
//    @ResponseBody
//    @CrossOrigin
//    public JsonResponse updateAddress (@RequestBody JSONObject jsonObject) {
//        System.out.println("jsonObject"+jsonObject);
//        Address address = new Address();
//        address.setId(jsonObject.getLong("id"));
//        address.setReceivername(jsonObject.getString("receivername"));
//        address.setUserId(jsonObject.getLong("user_id"));
//        address.setReceiverphone(jsonObject.getString("receiverphone"));
//        address.setReceiveraddress(jsonObject.getString("receiveraddress"));
//        address.setReceiverprovince(jsonObject.getJSONObject("values").getJSONObject("province").getString("value"));
//        address.setReceivercity(jsonObject.getJSONObject("values").getJSONObject("city").getString("value"));
//        address.setReceiverdistrict(jsonObject.getJSONObject("values").getJSONObject("area").getString("value"));
//        int num = addressService.updateAddress(address);
//        if(num>=1){
//            return JsonResponse.success(addressService.getAlladdress(address.getUserId()));
//        }
//        else return JsonResponse.failure(null);
//    }
}

