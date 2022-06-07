package com.example.mybatisplus.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void send(String msg){
        //System.out.println("发送消息："+msg.toString());
        //将对象转换成json字符串
        rabbitTemplate.convertAndSend("killExchange","kill.message",msg);
    }
}
