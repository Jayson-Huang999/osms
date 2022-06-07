package com.example.mybatisplus.model.Entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpecsInfo {
    private Long id;
    private String name;
    private String size;
    private Long productId;
    private BigDecimal price;
    private Integer stock;
    private Boolean checked;
    private Integer num;

}
