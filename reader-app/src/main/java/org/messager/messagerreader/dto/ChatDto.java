package org.messager.messagerreader.dto;

import lombok.Builder;
import lombok.Data;
import org.messager.messagerreader.entity.Chat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ChatDto {
    private String chatId;
    private String name;
    private String ownerId;
    private LocalDateTime createdAt;
    private List<String> participants;

    public static ChatDto fromChat(Chat chat) {
        return builder()
                .name(chat.getChatName())
                .chatId(chat.getId())
                .ownerId(chat.getOwnerId())
                .createdAt(chat.getCreatedAt())
                .participants(List.copyOf(chat.getParticipantIds()))
                .build();
    }
}
