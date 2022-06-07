package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Image;
import com.example.mybatisplus.mapper.ImageMapper;
import com.example.mybatisplus.service.ImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hhf
 * @since 2022-05-31
 */
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

}
