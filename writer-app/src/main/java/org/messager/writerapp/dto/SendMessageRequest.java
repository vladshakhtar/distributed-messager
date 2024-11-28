package org.messager.writerapp.dto;

import lombok.Data;

@Data
public class SendMessageRequest {
    private String chatId;
    private String message;
}
