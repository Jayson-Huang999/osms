package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("osms_user")
@ApiModel(value="User对象", description="用户信息表")
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

    private Long level;

    private LocalDateTime createtime;

    private LocalDateTime updatetime;

    private Integer isDeleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
