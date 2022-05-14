package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Comments;
import com.baomidou.mybatisplus.extension.service.IService;
import org.json.simple.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zp
 * @since 2022-03-03
 */
public interface CommentsService extends IService<Comments> {

    List<Map<String, Objects>> getComment(Long id);

//    void sendComment(JSONObject jsonObject);
}
