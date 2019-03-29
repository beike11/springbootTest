package com.stevenw.demo.config;

import com.stevenw.demo.filter.MyFilter;
import com.stevenw.demo.web.JsonReturnHandler;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.DeferredResultMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author stevenw
 * @date 2019/3/25
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport{

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

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

    @Bean
    public HandlerMethodReturnValueHandler completableFutureReturnValueHandler() {
        return new JsonReturnHandler();
    }

    @PostConstruct
    public void init() {
        final List<HandlerMethodReturnValueHandler> originalHandlers = new ArrayList<>(
                requestMappingHandlerAdapter().getReturnValueHandlers());

        final int deferredPos = obtainValueHandlerPosition(originalHandlers, DeferredResultMethodReturnValueHandler.class);
        // Add our handler directly after the deferred handler.
        originalHandlers.add(deferredPos + 1, completableFutureReturnValueHandler());
        requestMappingHandlerAdapter.setReturnValueHandlers(originalHandlers);
    }

    private int obtainValueHandlerPosition(final List<HandlerMethodReturnValueHandler> originalHandlers, Class<?> handlerClass) {
        for (int i = 0; i < originalHandlers.size(); i++) {
            final HandlerMethodReturnValueHandler valueHandler = originalHandlers.get(i);
            if (handlerClass.isAssignableFrom(valueHandler.getClass())) {
                return i;
            }
        }
        return -1;
    }

}
