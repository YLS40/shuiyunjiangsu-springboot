package com.example.common.config;

import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private JWTInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/")
                // 核心修改：把 /reservation/add 改成 /reservation/**，放行所有预约接口
                .excludePathPatterns("/login", "/register", "/files/**", "/alipay/**",
                        "/notice/**", "/travels/**", "/article/**", "/reservation/**");
    }

}