package com.example.mybatisplus.model.Entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.mybatisplus.model.domain.Activity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ActivityInfo {
    private Long productId;
    private Long preferentiallevel;
    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private Integer quantity;
    private Long status;
    private List<SpecsInfo> specsInfo;

}
