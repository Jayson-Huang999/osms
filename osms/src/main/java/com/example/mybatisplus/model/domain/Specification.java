package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商品规格
 * </p>
 *
 * @author zp
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("osms_specification")
@ApiModel(value="Specification对象", description="商品规格")
public class Specification extends Model<Specification> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Long productId;

    private String img;

    private String size;

    private BigDecimal price;

    private Integer stock;

    @TableField(exist = false)
    private Long goodsId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
