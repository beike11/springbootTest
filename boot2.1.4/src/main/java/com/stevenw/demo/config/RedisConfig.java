package com.stevenw.demo.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @author stevenw
 * @date 2019/4/23
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
    @Bean
    @Override
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj:objects) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
}
