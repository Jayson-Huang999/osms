package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.MD5Utils;
import com.example.mybatisplus.common.utls.SendCodeUtils;
import com.example.mybatisplus.common.utls.TokenUtils;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.model.domain.User;
import com.example.mybatisplus.service.MailService;
import com.example.mybatisplus.service.RedisService;
import com.example.mybatisplus.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
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
@Controller
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final Logger logger = LoggerFactory.getLogger( UserController.class );

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserMapper userMapper;
    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        User  user =  userService.getById(id);
        return JsonResponse.success(user);
    }

    /**
     * 描述：查询是否存在手机号
     *
     */
    @RequestMapping(value = "/phone",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse hasPhone(@RequestBody Map requestmap) throws Exception {
        String phone = (String) requestmap.get("phone");
        Boolean hasPhone = userService.hasPhone(phone);
        System.out.println(111);
        return JsonResponse.success(hasPhone);
    }

    /**
     * 描述：查询是否存在邮箱
     *
     */
    @RequestMapping(value = "/email",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse hasEmail(@RequestBody Map requestmap) throws Exception {
        String email = (String) requestmap.get("email");
        Boolean hasEmail = userService.hasEmail(email);
        Map<String,Boolean> map = new HashMap<>();
        map.put("msg",hasEmail);
        return JsonResponse.success(map);
    }

    /**
     * 描述：查询是否存在用户名
     *
     */
    @RequestMapping(value = "/username",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse hasUserName(@RequestBody Map requestmap) throws Exception {
        String username = (String) requestmap.get("username");
        Boolean hasUserName = userService.hasUserName(username);
        Map<String,Boolean> map = new HashMap<>();
        map.put("msg",hasUserName);
        return JsonResponse.success(map);
    }

    /**
     * 描述：通过邮箱查找账号和密码
     *
     */
    @RequestMapping(value = "/find/vertificationByEmail",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse findByEmail(@RequestBody Map requestmap) throws Exception {
        String email = (String) requestmap.get("email");
        String email_code = (String) requestmap.get("email_code");
        System.out.println(email_code);
        System.out.println(redisService.get(email));
        if (redisService.get(email).toString().equals(email_code)) {
            Map<String,String> map = userService.findByEmail(email);
            return JsonResponse.success(map);
        } else {
            return JsonResponse.success(null);
        }
    }
    /**
     * 描述：发送邮箱验证码
     *
     */
    @RequestMapping(value = "/find/getEmailCode",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse sendEmailCode( @RequestBody Map requestmap) throws Exception {
        String email = (String) requestmap.get("email");
        String msg= "";
        String code = SendCodeUtils.randomCode();
        String text= "您的验证码为：" + code +",两分钟内有效。请不要将验证码转发给他人，注意保护您的信息安全！";
        mailService.sendTextMailMessage(email,"验证码",text);
        //发送验证码，并存到session中,设置失效时间为120s
        redisService.set(email,code);
        msg += "邮箱验证码已发送";
        return JsonResponse.success(msg);
    }

    /**
     * 描述：先判断邮箱是否已经注册，再发送邮箱验证码
     *
     */
    @RequestMapping(value = "/find/EmailCode",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse emailCode( @RequestBody Map requestmap) throws Exception {
        String email = (String) requestmap.get("email");
        String msg= "";
        if(!userService.hasEmail(email)) {
            String code = SendCodeUtils.randomCode();
            String text= "您的验证码为：" + code +",两分钟内有效。请不要将验证码转发给他人，注意保护您的信息安全！";
            mailService.sendTextMailMessage(email,"验证码",text);
            //发送验证码，并存到session中,设置失效时间为120s
            redisService.set(email,code);
            msg += "邮箱验证码已发送";
        } else {
            msg += "邮箱已注册";
        }
        Map<String,String> map = new HashMap<>();
        map.put("msg",msg);
        return JsonResponse.success(map);
    }

    /**
     * 描述：修改头像、sex信息
     *
     */
    @RequestMapping(value = "/updateUserData",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateMessage(@RequestBody Map requestmap) {
        String image = (String) requestmap.get("headimage");
        String nickname = (String) requestmap.get("nickname");
        String phone = (String) requestmap.get("phone");
        short sex = ((Integer) requestmap.get("sex")).shortValue();
        Long id = ((Integer) requestmap.get("id")).longValue();
        String username = (String) requestmap.get("username");
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(sex);
        userService.updateMessage(image, sex, id,dateTime,nickname,phone,username);
        return JsonResponse.success(null);
    }

    /**
     * 描述：修改手机号信息,返回新的手机号信息
     *
     */
    @RequestMapping(value = "/update/phone",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updatePhone(@RequestBody Map requestmap) {
        String phone = (String) requestmap.get("phone");
        Long id = ((Integer) requestmap.get("id")).longValue();
        LocalDateTime dateTime = LocalDateTime.now();
        userService.updatePhone(phone, id, dateTime);
        User user = userService.getById(id);
        return JsonResponse.success(user.getPhone());
    }

    /**
     * 描述：修改邮箱信息
     *
     */
    @RequestMapping(value = "/update/email",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateEmail(@RequestBody Map requestmap) {
        String email = (String) requestmap.get("email");
        String emailCode = (String) requestmap.get("emailCode");
        Map<String,String> map = new HashMap<>();
        System.out.println(email +","+emailCode);
        System.out.println(redisService.get(email));
        Long id =((Integer) requestmap.get("id")).longValue();
        if(redisService.get(email).toString().equals(emailCode)) {
            LocalDateTime dateTime = LocalDateTime.now();
            userService.updateEmail(email, id,dateTime);
            System.out.println("修改成功");
            map.put("msg","修改成功");
        } else {
            map.put("msg","验证码不正确");
        }
        return JsonResponse.success(map);
    }

    /**
     * 描述：修改密码信息
     *
     */
    @RequestMapping(value = "/updatePwd",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateLoginInfo(@RequestBody Map requestmap) {
        String oldPwd = (String) requestmap.get("oldPwd");
        String newPwd = (String) requestmap.get("newPwd");
        Integer id =(Integer) requestmap.get("id");
        User user = userService.getById(id.longValue());
        Map<String,String> map = new HashMap<>();
        if (user.getPassword().equals(oldPwd)) {
            LocalDateTime dateTime = LocalDateTime.now();
            String password = MD5Utils.MD5Encode(newPwd);
            userService.updateLoginInfo( password, id.longValue(), dateTime);
            map.put("msg","密码修改成功");
        } else {
            map.put("msg","密码验证出错");
        }
        return JsonResponse.success(map);
    }
    /**
     * 描述：修改密码信息
     *
     */
    @RequestMapping(value = "/findPwd",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse findPwd(@RequestBody Map requestmap) {
        String password = (String) requestmap.get("password");
        //Long id = (Long)  requestmap.get("id");
        Long id =((Integer) requestmap.get("id")).longValue();
        Map<String,String> map = new HashMap<>();
        LocalDateTime dateTime = LocalDateTime.now();
        userService.updateLoginInfo( MD5Utils.MD5Encode(password), id.longValue(), dateTime);
        map.put("msg","密码修改成功");
//        return JsonResponse.success(map).setMessage("true");
        return JsonResponse.success(map);
    }

    /**
     * 描述：登录
     *
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(@RequestBody Map requestmap) throws Exception {
        String phone = (String) requestmap.get("phone");
        String password = (String) requestmap.get("password");
        System.out.println(phone +","+password);
        Long id = userService.login(phone,MD5Utils.MD5Encode(password));
        System.out.println(id);
        Map<String,Object> map = new HashMap<>();
        User user = userService.getById(id);
        System.out.println(user);
        if (user != null){
            JSONObject obj = new JSONObject();
            obj.put("id", user.getId());
            obj.put("user_id",user.getId());
            obj.put("nickname", user.getNickname());
            obj.put("token",TokenUtils.token(user.getUsername(), user.getPassword()) );
            map.put("data",obj);
            map.put("code",0);
            map.put("status",true);
        }else {
            map.put("code",10000);
            map.put("message","电话或密码错误！");
            map.put("status",false);
        }
        return map;
    }

    /**
     * 描述：注册
     *
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse signUp(@RequestBody Map requestmap) {
        String username = (String) requestmap.get("username");
        String password = (String) requestmap.get("password");
        String phone = (String) requestmap.get("phone");
        String email = (String) requestmap.get("email");
        String emailCode = (String) requestmap.get("emailCode");
        String nickname = (String) requestmap.get("nickname");
        System.out.println(emailCode);
        System.out.println(redisService.get(email));
        String msg = "";
        if(redisService.get(email).toString().equals(emailCode)){
            //先判断验证码是否正确
            LocalDateTime dateTime = LocalDateTime.now();
            User user = new User();
            user.setNickname(nickname);
            user.setUsername(username);
            user.setPassword(MD5Utils.MD5Encode(password));
            user.setPhone(phone);
            user.setEmail(email);
            user.setCreatetime(dateTime);
            System.out.println(user);
            userService.addUser(user);
            msg += "注册成功";
        }
        Map<String,String> map = new HashMap<>();
        map.put("msg",msg);
        return JsonResponse.success(map);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/deleteByID", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteByID(Long id) throws Exception {
        userService.deleteById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateUser(User  user) throws Exception {
        userService.updateById(user);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建User
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(User  user) throws Exception {
        userService.save(user);
        return JsonResponse.success(null);
    }
    /**
     * 描述：获取所有的用户信息
     * */
    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getAllUser(){
//        QueryWrapper<User> wrapper=new QueryWrapper<>();
//        wrapper.orderBy(true,true,"id","name");
//        wrapper.orderByDesc("id","name");
//        List<User> list=userService.list(wrapper);
        List<User> list=userService.list();
        System.out.println(list);
        return JsonResponse.success(list);
    }

    /**
     * 描述：模糊查询用户信息
     * */
    @RequestMapping(value = "/searchUserByKeyWord", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse searchUserByKeyWord(@RequestParam String keyword){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.like("username",keyword).or();
        wrapper.like("nickname",keyword).or();
        wrapper.like("email",keyword).or();
        wrapper.like("phone",keyword);
        List<User> list=userService.list(wrapper);
        System.out.println(list);
        return JsonResponse.success(list).setCode(0);
    }
    //确认收货后，增加用户积分，并更新会员等级
    @RequestMapping(value = "/updateUserScore", method = RequestMethod.PUT)
    @ResponseBody
    @CrossOrigin
    public JsonResponse updateUserScore(@RequestParam Long user_id,@RequestParam Long score){
        userMapper.updateUserScore(user_id,score);
        Long newScore=userService.getById(user_id).getScore();
        Integer level;
        if(newScore<5000)
            level=1;
        else if(newScore<10000)
            level=2;
        else if(newScore<20000)
            level=3;
        else if(newScore<50000)
            level=4;
        else
            level=5;
        UpdateWrapper<User> wrapper=new UpdateWrapper();
        wrapper.lambda().eq(User::getId,user_id);
        userService.update(new User().setLevel(level),wrapper);
        return JsonResponse.success(null);
    }
}
