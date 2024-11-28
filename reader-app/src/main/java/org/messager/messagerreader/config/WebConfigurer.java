package org.messager.messagerreader.config;

import lombok.RequiredArgsConstructor;
import org.messager.messagerreader.api.UserIdRequiredInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
public class WebConfigurer implements WebMvcConfigurer {

    private final UserIdRequiredInterceptor userIdRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userIdRequiredInterceptor)
                .addPathPatterns("/chat/*/messages");
    }

}
