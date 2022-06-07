package com.example.mybatisplus.model.domain;

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
 * 商品规格
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
