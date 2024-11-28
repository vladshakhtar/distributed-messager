package org.messager.messagerreader.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetMessagesResponse {
    private String chatId;
    private List<MessageDto> messages;
}
