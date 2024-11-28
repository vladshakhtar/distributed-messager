package org.messager.writerapp.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.messager.writerapp.config.UserIdException;
import org.messager.writerapp.repo.UserRepository;
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
            throw new UserIdException("Missing or empty 'userId' header");
        }

        if (!userRepository.existsById(userId)) {
            throw new UserIdException("User with id '" + userId + "' not found");
        }
        return true;
    }

}
