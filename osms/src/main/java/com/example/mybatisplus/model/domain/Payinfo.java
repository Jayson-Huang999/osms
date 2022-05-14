package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支付信息表
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("osms_payinfo")
@ApiModel(value="Payinfo对象", description="支付信息表")
public class Payinfo extends Model<Payinfo> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    private Long orderId;

    private Long status;

    private BigDecimal postage;

    private BigDecimal payment;

    private Integer platform;

    private Long paymenttype;

    private LocalDateTime paymenttime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
