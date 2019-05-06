package com.k2data.kbc.audit.config;

import com.k2data.kbc.audit.Interceptor.AuditLogInterceptor;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class AuditLogInterceptorConfig implements WebMvcConfigurer {


    @Value("${log.allow.origin}")
    private String logAllowOrigin;
    @Value("${log.intercept.origin}")
    private String logInterceptOrigin;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuditLogInterceptor()).addPathPatterns(stringOriginToList(logInterceptOrigin))
            .excludePathPatterns(stringOriginToList(logAllowOrigin));
    }

    private static List<String> stringOriginToList(String origins) {
        return Arrays.asList(origins.split(","));
    }

}
