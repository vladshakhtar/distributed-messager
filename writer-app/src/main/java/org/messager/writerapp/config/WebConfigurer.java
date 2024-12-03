package org.messager.writerapp.config;

import lombok.RequiredArgsConstructor;
import org.messager.writerapp.api.UserIdRequiredInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
public class WebConfigurer implements WebMvcConfigurer {

    private final UserIdRequiredInterceptor userIdRequiredInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userIdRequiredInterceptor)
                .addPathPatterns("/chat/*")
                .addPathPatterns("/message/*");
    }

}
