package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zp
 * @since 2022-03-03
 */
@Repository
public interface CommentsMapper extends BaseMapper<Comments> {

    List<Map<String, Objects>> getComment(Long id);

//    void sendComment(Object orderId, Object goodsId, Object goodsDetailId, Object content, Object score, Object clientId);

}
