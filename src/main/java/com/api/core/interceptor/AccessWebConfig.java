package com.api.core.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mqz
 * @description
 * @since 2020/4/23
 */
@Configuration
public class AccessWebConfig implements WebMvcConfigurer {

    @Bean
    public AccessInterceptor getAI(){
        return new AccessInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAI()).addPathPatterns("/token/**").excludePathPatterns("/doc.html");
    }
}
