package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.Seller;
import com.example.mybatisplus.service.SellerService;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 前端控制器
 *
 * @author tyl
 * @version v1.0
 * @since 2022-03-09
 * @since 2022-03-02
 */
@Controller
@CrossOrigin
@RequestMapping("/api/seller")
public class SellerController {

    private final Logger logger = LoggerFactory.getLogger(SellerController.class);

    @Autowired
    private SellerService sellerService;

    /**
     * 描述:登录Seller
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestBody Seller seller) {
        System.out.println(seller);
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Seller> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Seller::getAccount, seller.getAccount()).eq(Seller::getPassword, seller.getPassword());
        System.out.println(seller);
        Seller one = sellerService.getOne(wrapper);
        System.out.println(one);
        if (Objects.nonNull(one)) {
            if (one.getStatus() == 2L) {
                JSONObject obj = new JSONObject();
                obj.put("id", one.getId());
                obj.put("name", one.getName());
                obj.put("token", "2234567890");
                map.put("data", obj);
                map.put("code", 0);
                map.put("status", 200);
            } else {
                map.put("code", 10000);
                map.put("message", "账号正在审核中！");
                map.put("status", 300);
            }
        } else {
            map.put("code", 10000);
            map.put("message", "账号或密码错误！");
            map.put("status", 300);
        }
        System.out.println("map="+map);
        return map;
    }

    /**
     * 说明：商家入驻申请
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse signUp(@RequestBody Seller seller) {
        sellerService.save(seller);
        return JsonResponse.success(null).setCode(0).setMessage("申请成功！");
    }

    /**
     * 保存修改
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveChange", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse saveChange(@RequestBody Map<String, Object> obj) throws Exception {
        System.out.println(obj);
        QueryWrapper<Seller> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Seller::getPassword, obj.get("oldPwd")).eq(Seller::getAccount, obj.get("account"));
        Seller one = sellerService.getOne(wrapper);
        if (one == null) {
            return JsonResponse.success(null).setCode(1000).setMessage("账号或密码错误！");
        } else {
            UpdateWrapper<Seller> wrapper1 = new UpdateWrapper<>();
            wrapper1.set("name", obj.get("sName"));
            wrapper1.set("phone", obj.get("phone"));
            wrapper1.set("password", obj.get("newPwd"));
            wrapper1.set("location", obj.get("sLocation"));
            wrapper1.in("account", obj.get("account"));
            sellerService.update(wrapper1);
            return JsonResponse.success(null).setCode(0).setMessage("修改成功！");
        }
    }

    /**
     * 描述：获取所有的商家信息
     */
    @RequestMapping(value = "/getAllSeller", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getAllSeller() {
        List<Seller> list = sellerService.list();
        System.out.println(list);
        return JsonResponse.success(list);
    }

    /**
     * 描述:根据商家的status查找信息
     */
    @RequestMapping(value = "/getAllSellersByStatus", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAllSellersByStatus(@RequestParam Long status) {
        List<Seller> sellers = null;
        if (status != 0L) {
            QueryWrapper<Seller> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Seller::getStatus, status);
            sellers = sellerService.list(wrapper);
        } else {
            sellers = sellerService.list();
        }
        System.out.println(sellers);
        return JsonResponse.success(sellers);
    }

    /**
     * 描述：模糊查询用户信息
     */
    @RequestMapping(value = "/searchSellerByKeyWordAndStatus", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse searchSellerByKeyWordAndStatus(@RequestBody Map<String, Object> map) {
        System.out.println(map);
        QueryWrapper<Seller> wrapper = new QueryWrapper<>();
        wrapper.and(
                wrap -> wrap
                        .like("name", map.get("keyword")).or()
                        .like("phone", map.get("keyword")).or()
                        .like("account", map.get("keyword")).or()
                        .like("location", map.get("keyword"))
        );
        if (!map.get("status").equals(0)) {
            System.out.println(111);
            wrapper.lambda().eq(Seller::getStatus, map.get("status"));
        }

        List<Seller> list = sellerService.list(wrapper);
        System.out.println(list);
        return JsonResponse.success(list).setCode(0);
    }

    /**
     * 描述：根据Id注销违规商家账户
     */
    @RequestMapping(value = "/changeSellerByID", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse changeSellerByID(@RequestBody Map<String, Object> map) throws Exception {
        System.out.println(map);
        UpdateWrapper<Seller> wrapper = new UpdateWrapper<>();
        if (map.get("flag").equals(1)) {
            wrapper.set("status", 2).in("id", map.get("id"));
        } else if (map.get("flag").equals(2)) {
            wrapper.set("status", 3).in("id", map.get("id"));
        } else if (map.get("flag").equals(3)) {
            wrapper.set("status", 4).in("id", map.get("id"));
        } else {
            wrapper.set("status", 1).in("id", map.get("id"));
        }
        sellerService.update(wrapper);
        return JsonResponse.success(null).setCode(0);
    }
}

