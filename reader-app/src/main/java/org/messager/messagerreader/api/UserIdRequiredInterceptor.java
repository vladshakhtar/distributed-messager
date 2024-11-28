package org.messager.messagerreader.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.messager.messagerreader.repo.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class UserIdRequiredInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader("userId");
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("Missing or empty 'userId' header");
        }

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User with id '" + userId + "' not found");
        }
        return true;
    }

}
