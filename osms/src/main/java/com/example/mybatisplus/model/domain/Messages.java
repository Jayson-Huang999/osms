package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zp
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("osms_messages")
@ApiModel(value="Messages对象", description="")
public class Messages extends Model<Messages> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    @TableField("seller_id")
    private Long sellerId;

    private Long productId;

    private String content;

    private Integer state;

    private LocalDateTime createtime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
