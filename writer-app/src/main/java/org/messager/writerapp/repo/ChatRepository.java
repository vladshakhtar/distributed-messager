package org.messager.writerapp.repo;

import org.messager.writerapp.entity.Chat;
import org.messager.writerapp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

}
