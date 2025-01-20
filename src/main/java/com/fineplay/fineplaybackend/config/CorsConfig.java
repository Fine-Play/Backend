// 일단 이게 안됨
package com.fineplay.fineplaybackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry
                .addMapping("/**") // 모든 경로에 대해 cors 적용
                .allowedMethods("*")
                .allowedOrigins("*"); // 위험함. 나중에 수정 필요
    }
}
