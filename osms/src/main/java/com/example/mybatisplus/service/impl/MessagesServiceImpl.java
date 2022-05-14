package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Messages;
import com.example.mybatisplus.mapper.MessagesMapper;
import com.example.mybatisplus.service.MessagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zp
 * @since 2022-03-03
 */
@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages> implements MessagesService {

}
