package org.messager.messagerreader.dto;

import lombok.Builder;
import lombok.Data;
import org.messager.messagerreader.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserDto {

    private String userId;
    private String username;
    private LocalDateTime createdAt;
    private List<String> chats;

    public static UserDto fromUser(User user) {
        return builder()
                .userId(user.getId())
                .chats(List.copyOf(user.getChatIds()))
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
