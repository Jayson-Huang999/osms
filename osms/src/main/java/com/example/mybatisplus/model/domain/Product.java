package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商品信息表
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("osms_product")
@ApiModel(value="Product对象", description="商品信息表")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long sellerId;

    private Long brandId;

    private Long categoryId;

    private String name;

    private String describ;

    private Long status;

    private LocalDateTime createtime;

    private LocalDateTime updatetime;

    @TableField("is_deleted")
    @TableLogic  //逻辑删除注解 默认值为0表示未删除
    private boolean deleted;

    @TableField(exist = false)
    private List<Image> images;

    @TableField(exist = false)
    private List<Specification> specs;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
