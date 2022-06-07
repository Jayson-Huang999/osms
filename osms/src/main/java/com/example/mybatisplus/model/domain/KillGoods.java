package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import java.math.BigDecimal;
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
 * 
 * </p>
 *
 * @author zp
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("osms_kill_goods")
@ApiModel(value="KillGoods对象", description="")
public class KillGoods extends Model<KillGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long productId;

    private Long activityId;

    private Long specificationId;

    private Integer stock;

    private BigDecimal price;


    public KillGoods(Long productId, Long activityId, Long specificationId, Integer stock, BigDecimal price) {
        this.productId = productId;
        this.activityId = activityId;
        this.specificationId = specificationId;
        this.stock = stock;
        this.price = price;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
