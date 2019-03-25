package com.stevenw.demo.config;

import com.stevenw.demo.filter.MyFilter;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stevenw
 * @date 2019/3/25
 */
@Configuration
public class WebConfiguration {
    @Bean
    public RemoteIpFilter remoteIpFilter(){
        return  new RemoteIpFilter();
    }
    @Bean
    public FilterRegistrationBean filterRegistration(){
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new MyFilter());
        filterRegistration.addUrlPatterns("/*");
        Map initParamsMap = new HashMap();
        initParamsMap.put("encoding","UTF-8");
        filterRegistration.setInitParameters(initParamsMap);
        filterRegistration.setName("MyFilter");
        filterRegistration.setOrder(1);
        return  filterRegistration;
    }
}
