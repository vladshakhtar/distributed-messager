package org.messager.writerapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateChatRequest {

    private String chatName;
    private List<String> participants;

}
