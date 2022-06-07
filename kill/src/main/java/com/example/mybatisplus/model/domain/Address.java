package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 收货地址信息表
 * </p>
 *
 * @author hhf
 * @since 2022-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("osms_address")
@ApiModel(value="Address对象", description="收货地址信息表")
public class Address extends Model<Address> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String receivername;

    private String receiverphone;

    private String receiverprovince;

    private String receivercity;

    private String receiverdistrict;

    private String receiveraddress;

    private Boolean isDefault;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
