package org.messager.writerapp.dto;

import lombok.Builder;
import lombok.Data;
import org.messager.writerapp.entity.Chat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ChatResponse {

    private String chatId;
    private String chatName;
    private String ownerId;
    private List<String> participantIds;
    private LocalDateTime createdAt;

    public static ChatResponse fromChat(Chat chat) {
        return ChatResponse.builder()
                .chatId(chat.getId())
                .chatName(chat.getChatName())
                .createdAt(chat.getCreatedAt())
                .ownerId(chat.getOwnerId())
                .participantIds(List.copyOf(chat.getParticipantIds()))
                .build();
    }
}
