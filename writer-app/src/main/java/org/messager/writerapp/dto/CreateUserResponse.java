package org.messager.writerapp.dto;

import lombok.Builder;
import lombok.Data;
import org.messager.writerapp.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreateUserResponse {

    private String id;
    private String username;
    private List<String> chatIds;
    private LocalDateTime createdAt;

    public static CreateUserResponse fromUser(User user) {
        return CreateUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .chatIds(List.copyOf(user.getChatIds()))
                .createdAt(user.getCreatedAt())
                .build();
    }
}
