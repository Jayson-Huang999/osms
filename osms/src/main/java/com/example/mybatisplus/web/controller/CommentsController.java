package com.example.mybatisplus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.common.utls.SecurityUtils;
import com.example.mybatisplus.common.utls.SessionUtils;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.service.CommentsService;
import com.example.mybatisplus.model.domain.Comments;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 *
 *  前端控制器
 *
 *
 * @author zp
 * @since 2022-03-03
 * @version v1.0
 */
@Controller
@RequestMapping("/api/comments")
public class CommentsController {

    private final Logger logger = LoggerFactory.getLogger( CommentsController.class );

    @Autowired
    private CommentsService commentsService;

    /**
    * 描述：获取商品评论
    *
    */
    @RequestMapping(value = "getGoodsComment", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getComment(@RequestParam Long id)throws Exception {
//        QueryWrapper<Comments> wrapper = new QueryWrapper<>();
//        wrapper.lambda().eq(Comments::getProductId,id);
//        List<Comments> comments = commentsService.list(wrapper);
        List<Map<String,Objects>> comments = commentsService.getComment(id);
        return JsonResponse.success(comments).setCode(0);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        commentsService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateComments(Comments comments) throws Exception {
        commentsService.updateById(comments);
        return JsonResponse.success(null);
    }


    /**
    * 描述:在用户评论后在评论表创建一个comments
    *
    */
    @RequestMapping(value = "/sendComment", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse sendComment(@RequestBody Comments comments) throws Exception {
        commentsService.save(comments.setCreatetime(LocalDateTime.now()));
        return JsonResponse.success(null);
    }
}

