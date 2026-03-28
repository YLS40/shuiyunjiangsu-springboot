package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

@SpringBootApplication
@MapperScan(value = "com.example.mapper", annotationClass = org.apache.ibatis.annotations.Mapper.class)
public class SpringbootApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringbootApplication.class)
                .properties("spring.boot.runner.enabled=false")
                .properties("spring.main.allow-bean-definition-overriding=true")
                .run(args);
    }

    @Bean("ddlApplicationRunner")
    @ConditionalOnMissingBean(name = "ddlApplicationRunner")
    public ApplicationRunner ddlApplicationRunner() {
        return args -> {
        };
    }

}