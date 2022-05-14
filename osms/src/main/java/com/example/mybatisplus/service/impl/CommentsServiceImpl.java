package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.model.domain.Comments;
import com.example.mybatisplus.mapper.CommentsMapper;
import com.example.mybatisplus.service.CommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zp
 * @since 2022-03-03
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public List<Map<String, Objects>> getComment(Long id) {
        List<Map<String, Objects>> maps = commentsMapper.getComment(id);
        return maps;
    }

//    @Override
//    public void sendComment(JSONObject jsonObject) {
//        commentsMapper.sendComment(jsonObject.get("order_id"),jsonObject.get("product_id")
//            ,jsonObject.get("specification_id"),jsonObject.get("content")
//                ,jsonObject.get("score"),jsonObject.get("user_id"));
//    }
}
