package com.example.mybatisplus.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author tbb
 *
 */
@Configuration
public class RedissonConfig
{
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException
    {
        Config config = new Config();
        //这里设置redis的地址和密码
        config.useSingleServer()
                .setAddress("redis://localhost:6379");
        return Redisson.create(config);
        /*RedissonClient redisson = Redisson.create(Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream()));
        return redisson;*/
    }
}
