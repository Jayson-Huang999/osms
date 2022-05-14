package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.model.domain.Admins;
import com.example.mybatisplus.service.AdminsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.AdminsService;
import com.example.mybatisplus.model.domain.Admins;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 前端控制器
 *
 * @author zp
 * @version v1.0
 * @since 2022-03-02
 */
@Controller
@RequestMapping("/api/admins")
@CrossOrigin
public class AdminsController {

    private final Logger logger = LoggerFactory.getLogger(AdminsController.class);

    @Autowired
    private AdminsService adminsService;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Admins admins = adminsService.getById(id);
        return JsonResponse.success(admins);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        adminsService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateAdmins(Admins admins) throws Exception {
        adminsService.updateById(admins);
        return JsonResponse.success(null);
    }


    /**
     * 描述:创建Admins
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Admins admins) throws Exception {
        adminsService.save(admins);
        return JsonResponse.success(null);
    }

    /**
     * 管理员登录验证
     *
     * @param admins
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse login(@RequestBody Admins admins) throws Exception {
        System.out.println(admins);
        QueryWrapper<Admins> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Admins::getPassword, admins.getPassword()).eq(Admins::getAccount, admins.getAccount());
        Admins one = adminsService.getOne(wrapper);
        System.out.println(one);
        if (!Objects.nonNull(one)) {
            return JsonResponse.success(null)
                    .setCode(10000)
                    .setMessage("账号或密码错误！")
                    .setStatus(false);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", one.getId());
        map.put("admin_id", one.getId());
        map.put("name", one.getName());
        map.put("token", "1234567890");
        return JsonResponse.success(map)
                .setCode(0)
                .setStatus(true);
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
    public JsonResponse login(@RequestBody Map<String,Object> obj) throws Exception {
        System.out.println(obj);
        QueryWrapper<Admins> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Admins::getPassword, obj.get("oldPwd")).eq(Admins::getAccount, obj.get("account"));
        Admins one = adminsService.getOne(wrapper);
        if (one == null) {
            return JsonResponse.success(null).setCode(1000).setMessage("账号或密码错误！");
        } else {
            UpdateWrapper<Admins> wrapper1 = new UpdateWrapper<>();
            wrapper1.set("name",obj.get("aName"));
            wrapper1.set("password", obj.get("newPwd"));
            wrapper1.in("account", obj.get("account"));
            adminsService.update(wrapper1);
            return JsonResponse.success(null).setCode(0).setMessage("修改成功！");
        }
    }
}

