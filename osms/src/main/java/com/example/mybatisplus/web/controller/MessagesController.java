package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.TokenUtils;
import com.example.mybatisplus.model.domain.Messages;
import com.example.mybatisplus.model.domain.Product;
import com.example.mybatisplus.model.domain.Replies;
import com.example.mybatisplus.model.domain.User;
import com.example.mybatisplus.service.MessagesService;
import com.example.mybatisplus.service.ProductService;
import com.example.mybatisplus.service.RepliesService;
import com.example.mybatisplus.service.UserService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 前端控制器
 *
 *
 * @author zp
 * @since 2022-03-03
 * @version v1.0
 */
@Controller
@RequestMapping("/api/messages")
@CrossOrigin
public class MessagesController {

    private final Logger logger = LoggerFactory.getLogger(MessagesController.class);

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private RepliesService repliesService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    /**
     * 描述：根据goods_Id 查询其所有的消息
     */
    @RequestMapping(value = "/getGoodsMsg", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getGoodsMsg(@RequestParam Long gid) throws Exception {
        List<Map<String, Object>> msgList = new ArrayList<>();
        QueryWrapper<Messages> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Messages::getProductId, gid);
        wrapper.orderByDesc("createtime");
        List<Messages> messages = messagesService.list(wrapper);
        System.out.println(messages);
        for (Messages msg : messages) {
            String nickname = "";
            JSONObject replyObj = new JSONObject();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            QueryWrapper<User> wrapper1 = new QueryWrapper<>();
            wrapper1.lambda().eq(User::getId, msg.getUserId());
            User user = userService.getOne(wrapper1,false);

            QueryWrapper<Replies> wrapper2 = new QueryWrapper<>();
            wrapper2.lambda().eq(Replies::getMessagesId, msg.getId());
            Replies reply = repliesService.getOne(wrapper2,false);


            if (user == null) {
                user=new User();
                nickname = "该用户已注销";
                System.out.println("查询到的user为空！");
            } else {
                nickname = user.getNickname();
                System.out.println(user);
            }

            if (reply == null) {
                System.out.println("查询到的reply为空！");
                replyObj.put("id",null);
                replyObj.put("content",null);
                replyObj.put("time",null);
            } else {
                replyObj.put("id", reply.getId());
                replyObj.put("content", reply.getContent());
                replyObj.put("time", reply.getCreatetime().format(formatter));
                System.out.println(replyObj);
            }
            JSONObject tmpMsg = new JSONObject();
            tmpMsg.put("id", msg.getId());
            tmpMsg.put("content", msg.getContent());
            tmpMsg.put("state", msg.getState());
            tmpMsg.put("asker", nickname);
            tmpMsg.put("time", msg.getCreatetime().format(formatter));
            tmpMsg.put("reply", replyObj);
            msgList.add(tmpMsg);
        }
        System.out.println("msgList" + msgList);
        return JsonResponse.success(msgList).setCode(0);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        messagesService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateMessages(Messages messages) throws Exception {
        messagesService.updateById(messages);
        return JsonResponse.success(null);
    }


    /**
     * 描述:创建Messages
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Messages messages) throws Exception {
        messagesService.save(messages);
        return JsonResponse.success(null);
    }


    /**
     * 说明：保存用户向商家的提问
     */
    @RequestMapping(value = "/askProductMsg", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse askProductMsg(@RequestBody Messages messages) throws Exception {
        messagesService.save(messages);
        return JsonResponse.success(null).setCode(0).setStatus(true);
    }

    /**
     * 获取没有回复的信息
     */
    @RequestMapping(value = "/getNoReplyMsg", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse getNoReplyMsg(@RequestBody Messages messages) throws Exception {
        messagesService.save(messages);
        return JsonResponse.success(null).setCode(0).setStatus(true);
    }

    /**
     * 根据商家id查询所有的未回复信息
     */
    @RequestMapping(value = "/getNoReplyMsgBySid", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getNoReplyMsgBySid(@RequestParam Long id) throws Exception {
        QueryWrapper<Messages> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Messages::getSellerId, id);
        wrapper.lambda().eq(Messages::getState, 1);
        wrapper.orderByDesc("createtime");


        List<Messages> noReplyMsgs = messagesService.list(wrapper);
        System.out.println("noReplyMsgs:"+noReplyMsgs);
        List<Map<String, Object>> messagesList = new ArrayList<>();
        if (noReplyMsgs.size() == 0) {
            return JsonResponse.success(null).setCode(0).setStatus(true).setMessage("暂无数据！");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (Messages msg : noReplyMsgs) {
                QueryWrapper<User> wrapper1 = new QueryWrapper<>();
                QueryWrapper<Product> wrapper2 = new QueryWrapper<>();

                JSONObject userObj = new JSONObject();
                JSONObject productObj = new JSONObject();
                JSONObject tempMsg = new JSONObject();

                wrapper1.lambda().eq(User::getId, msg.getUserId());
                User user = userService.getOne(wrapper1,false);
                if (user == null) {
                    user=new User();
                    user.setNickname("该用户已被注销");
                    user.setHeadimage("");
                }
                wrapper2.lambda().eq(Product::getId, msg.getProductId());
                Product product = productService.getOne(wrapper2,false);
                if (product == null) {
                    product=new Product();
                    product.setId(0L);
                    product.setName("该商品已下架");
                }
                userObj.put("name", user.getNickname());
                userObj.put("headimage", user.getHeadimage());
                productObj.put("id", product.getId());
                productObj.put("name", product.getName());
                tempMsg.put("id", msg.getId());
                tempMsg.put("content", msg.getContent());
                tempMsg.put("time", msg.getCreatetime().format(formatter));
                tempMsg.put("user", userObj);
                tempMsg.put("goods", productObj);

                messagesList.add(tempMsg);
            }
        }
        System.out.println(messagesList);
        return JsonResponse.success(messagesList).setCode(0);
    }


    /**
     * 描述:获取所有回复的信息
     */
    @RequestMapping(value = "/getRepliedMsgBySid", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getRepliedMsgBySid(@RequestParam Long id) throws Exception {

        QueryWrapper<Messages> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Messages::getState, 0).eq(Messages::getSellerId, id);
        wrapper.orderByDesc("createtime");
        List<Messages> repliedMsgs = messagesService.list(wrapper);

        List<Map<String, Object>> messagesList = new ArrayList<>();
        if (repliedMsgs.size() == 0) {
            return JsonResponse.success(null).setCode(0).setStatus(true).setMessage("暂无数据！");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (Messages msg : repliedMsgs) {
                QueryWrapper<User> wrapper1=new QueryWrapper<>();
                QueryWrapper<Product> wrapper2=new QueryWrapper<>();
                QueryWrapper<Replies> wrapper3=new QueryWrapper<>();

                JSONObject userObj = new JSONObject();
                JSONObject productObj = new JSONObject();
                JSONObject tempMsg = new JSONObject();

                wrapper1.lambda().eq(User::getId, msg.getUserId());
                User user=userService.getOne(wrapper1,false);
                if (user == null) {
                    user=new User();
                    user.setNickname("该用户已被注销");
                    user.setHeadimage("");
                }
                wrapper2.lambda().eq(Product::getId, msg.getProductId());
                Product product = productService.getOne(wrapper2,false);
                if (product == null) {
                    product=new Product();
                    product.setId(0L);
                    product.setName("该商品已下架");
                }
                wrapper3.lambda().eq(Replies::getMessagesId, msg.getId());
                Replies replies = repliesService.getOne(wrapper3,false);
                if (replies==null){
                    replies=new Replies();
                    replies.setContent("恢复不存在！");
                }


                userObj.put("name", user.getNickname());
                userObj.put("headimage", user.getHeadimage());
                productObj.put("id", product.getId());
                productObj.put("name", product.getName());
                tempMsg.put("id", msg.getId());
                tempMsg.put("content", msg.getContent());
                tempMsg.put("time", msg.getCreatetime().format(formatter));
                tempMsg.put("replyContent", replies.getContent());
                tempMsg.put("user", userObj);
                tempMsg.put("goods", productObj);
                messagesList.add(tempMsg);
            }
        }
        System.out.println(messagesList);
        return JsonResponse.success(messagesList).setCode(0);
    }
}

