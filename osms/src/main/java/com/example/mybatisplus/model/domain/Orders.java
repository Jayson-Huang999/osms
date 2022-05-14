package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("osms_orders")
@ApiModel(value="Orders对象", description="订单明细表")
public class Orders extends Model<Orders> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long addressId;

    private Long specificationId;

    private Long userId;

    private Long status;

    private Integer quantity;

    private BigDecimal preferentialprice;

    private BigDecimal totalprice;

    private LocalDateTime createtime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
