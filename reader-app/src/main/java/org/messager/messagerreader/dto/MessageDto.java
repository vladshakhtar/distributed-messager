package org.messager.messagerreader.dto;

import lombok.Builder;
import lombok.Data;
import org.messager.messagerreader.entity.Message;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageDto {

    private String chatId;
    private String senderUsername;
    private String text;
    private LocalDateTime sentAt;

    public static MessageDto fromMessage(Message message) {
        return MessageDto.builder()
                .chatId(message.getChatId())
                .senderUsername(message.getSenderName())
                .text(message.getText())
                .sentAt(message.getSentAt())
                .build();
    }
}
