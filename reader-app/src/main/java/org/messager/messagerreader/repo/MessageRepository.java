package org.messager.messagerreader.repo;

import org.messager.messagerreader.entity.Message;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    default List<Message> getMessagesByTimePeriod(String chatId, LocalDateTime after, LocalDateTime before) {
        return getMessagesByChatIdAndSentAtBetweenOrderBySentAtDesc(chatId, after, before);
    }

    default List<Message> getLastMessages(String chatId, int lastMessagesCount) {
        Pageable pageable = PageRequest.of(0, lastMessagesCount, Sort.by(Sort.Direction.DESC, "sentAt"));
        return findByChatId(chatId, pageable);
    }

    List<Message> getMessagesByChatIdAndSentAtBetweenOrderBySentAtDesc(String chatId, LocalDateTime after, LocalDateTime before);
    List<Message> findByChatId(String chatId, Pageable pageable);

}
