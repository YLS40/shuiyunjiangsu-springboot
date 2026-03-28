package com.example.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 关键修改：addAllowedOriginPattern 替代 addAllowedOrigin，适配Spring 6+规范
        corsConfiguration.addAllowedOriginPattern("*"); // 1 设置访问源地址（支持通配符）
        corsConfiguration.addAllowedHeader("*"); // 2 设置访问源请求头
        corsConfiguration.addAllowedMethod("*"); // 3 设置访问源请求方法
        corsConfiguration.setAllowCredentials(true); // 新增：允许携带Cookie（前端请求需要）
        source.registerCorsConfiguration("/**", corsConfiguration); // 4 对所有接口配置跨域
        return new CorsFilter(source);
    }
}