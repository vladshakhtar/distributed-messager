package org.messager.writerapp.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "chats")
@Data
@Builder
public class Chat {

    @Id
    private String id;
    private String chatName;
    private LocalDateTime createdAt;
    private String ownerId;
    private Set<String> participantIds;

}
