package com.example.mybatisplus.common.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.concurrent.TimeUnit;

//自定义参数，在前端请求传到后端时进行，对请求的内容进行判断，避免每一个接口都要判断用户是否登录
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * test接口，1秒钟生成1个令牌，也就是1秒中允许一个人访问
         */
        ;
        registry.addInterceptor(new RateLimiterInterceptor(RateLimiter.create(6000, 5, TimeUnit.SECONDS)))
                .addPathPatterns("/activity/setKill");
    }
}
