package com.tr.mita.config;

import com.tr.mita.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * 提前new出 防止redis失效
     * @return
     */
    @Bean
    public TokenInterceptor getTokenInterceptor(){
        return new TokenInterceptor();
    }
	/**
     * interceptor配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenInterceptor())
                //添加需要验证登录用户操作权限的请求
                .addPathPatterns("/**")
                //排除不需要验证登录用户操作权限的请求
                .excludePathPatterns("/verify/**")
                .excludePathPatterns("/login/**");
    }

}
