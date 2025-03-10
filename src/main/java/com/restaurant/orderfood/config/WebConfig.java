package com.restaurant.orderfood.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Cấu hình để đảm bảo xử lý tiếng Việt đúng cách trong ứng dụng Spring Boot
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Cấu hình StringHttpMessageConverter để sử dụng UTF-8
     */
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

    /**
     * Thêm converter vào danh sách các converter
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public void postHandle(HttpServletRequest request,
                    jakarta.servlet.http.HttpServletResponse response,
                    Object handler,
                    ModelAndView modelAndView) throws Exception {
                if (modelAndView != null) {
                    String requestURI = request.getRequestURI();
                    boolean isAdminPage = requestURI != null && requestURI.contains("admin");
                    modelAndView.addObject("isAdminPage", isAdminPage);
                }
            }
        });
    }
}