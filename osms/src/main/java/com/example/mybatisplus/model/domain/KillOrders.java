package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zp
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("osms_kill_orders")
@ApiModel(value="KillOrders对象", description="")
public class KillOrders extends Model<KillOrders> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    private Long goodsId;

    private Long userId;

    private Long addressId;

    private Integer status;

    private BigDecimal price;

    @TableField("createTime")
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
