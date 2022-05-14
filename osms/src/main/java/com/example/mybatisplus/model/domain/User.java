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
 * �û���Ϣ��
 * </p>
 *
 * @author lgl
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("osms_user")
@ApiModel(value="User对象", description="�û���Ϣ��")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private Boolean sex;

    private String phone;

    private String email;

    private String headimage;

    private Long role;

    private Long score;

    private Integer level;

    private LocalDateTime createtime;

    private LocalDateTime updatetime;

    @TableLogic//表示逻辑删除
    @TableField("is_deleted")
    private Boolean deleted;

    public User() {
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
