package com.example.mybatisplus.common.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置 by CHENYB date 2019-07-29
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Bean
    @Override
    public Executor getAsyncExecutor() {
        //线程池设置
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize( 8 );//核心线程
        taskExecutor.setMaxPoolSize( 16 );//最大线程
        taskExecutor.setQueueCapacity( 64 );//队列大小
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setThreadNamePrefix( "async-token-" );
        taskExecutor.initialize();
        return taskExecutor;
    }



    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SpringAsyncExceptionHandler();
    }

    class SpringAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            //logger.error("Exception occurs in async method", throwable.getMessage());
        }
    }
}
