package org.messager.messagerreader.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetMessagesRequest {

    private LocalDateTime from;
    private LocalDateTime to;

    private Integer last;
}
