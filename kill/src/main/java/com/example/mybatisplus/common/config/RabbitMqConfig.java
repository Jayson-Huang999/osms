package com.example.mybatisplus.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    /**
     * 发送者-->交换机-->队列-->消费者（监听队列）
     * 发送者:通过路由键发给交换机
     *交换机：
     *  Fanout交换机:广播模式，不需要路由键
     *
     * 绑定:将交换机和队列进行绑定
     * 队列：交换机通过路由键把消息发送给队列缓冲区
     * 消费者监听队列获取消息
     */
    @Bean
    public Queue queue(){
        return new Queue("killQueue",true);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("killExchange");
    }

   @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange()).with("kill.#");
   }


}
