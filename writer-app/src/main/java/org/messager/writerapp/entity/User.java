package org.messager.writerapp.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "users")
@Data
@Builder
public class User {

    @Id
    private String id;
    private String username;
    private Set<String> chatIds;
    private LocalDateTime createdAt;

}
