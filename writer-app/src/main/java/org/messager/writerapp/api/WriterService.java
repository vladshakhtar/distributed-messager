package org.messager.writerapp.api;

import lombok.RequiredArgsConstructor;
import org.messager.writerapp.dto.*;
import org.messager.writerapp.entity.Chat;
import org.messager.writerapp.entity.Message;
import org.messager.writerapp.entity.User;
import org.messager.writerapp.repo.ChatRepository;
import org.messager.writerapp.repo.MessageRepository;
import org.messager.writerapp.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class WriterService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    public CreateUserResponse createUser(CreateUserRequest request) {
        var id = UUID.randomUUID().toString();
        var user = User.builder()
                .id(id)
                .chatIds(Set.of())
                .username(request.getUsername())
                .createdAt(LocalDateTime.now())
                .build();

        return CreateUserResponse.fromUser(userRepository.save(user));
    }

    public ChatResponse createChat(CreateChatRequest request, String userId) {
        var id = UUID.randomUUID().toString();

        if (isNull(request.getParticipants())) {
            request.setParticipants(List.of());
        }

        request.getParticipants().forEach(participantId -> {
            if (!userRepository.existsById(participantId)) {
                throw new IllegalArgumentException("User with id '" + participantId + "' not found");
            }
        });

        var participants = new HashSet<>(request.getParticipants());
        participants.add(userId);

        var chat = Chat.builder()
                .id(id)
                .chatName(request.getChatName())
                .createdAt(LocalDateTime.now())
                .ownerId(userId)
                .participantIds(participants)
                .build();

        return ChatResponse.fromChat(chatRepository.save(chat));
    }

    public void deleteChat(String chatId, String userId) {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isPresent()) {
            if (Objects.equals(chat.get().getOwnerId(), userId)) {
                chatRepository.deleteById(chatId);
            } else {
                throw new IllegalArgumentException("Only owner can delete chat.");
            }
        } else {
            throw new IllegalArgumentException("Chat with id '" + chatId + "' not found");
        }
    }

    public ChatResponse joinChat(String chatId, String userId) {
        var user = getUser(userId);
        var chat = getChat(chatId);
        if (chat.getParticipantIds().contains(userId)) {
            throw new IllegalArgumentException("User already in chat.");
        }
        chat.getParticipantIds().add(userId);
        user.getChatIds().add(chatId);
        chatRepository.save(chat);
        userRepository.save(user);
        return ChatResponse.fromChat(chat);

    }

    public void leaveChat(String chatId, String userId) {
        var chat = getChat(chatId);
        var user = getUser(userId);
        if (chat.getParticipantIds().contains(userId) || user.getChatIds().contains(chatId)) {
            chat.getParticipantIds().remove(userId);
            user.getChatIds().remove(chatId);
            chatRepository.save(chat);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not in chat.");
        }
    }

    public void sendMessage(SendMessageRequest request, String userId) {
        validateSendMessageRequest(request);
        var chat = getChat(request.getChatId());
        var user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User doesn't exist."));
        if (chat.getParticipantIds().contains(userId)) {
            messageRepository.save(createMessage(request, user));
        } else {
            throw new IllegalArgumentException("User not in chat.");
        }
    }

    private void validateSendMessageRequest(SendMessageRequest request) {
        if (request.getMessage() == null || request.getMessage().isEmpty()) {
            throw new IllegalArgumentException("Message is empty.");
        }
        if (request.getChatId() == null || request.getChatId().isEmpty()) {
            throw new IllegalArgumentException("Chat id is empty.");
        }
    }

    private User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id '" + userId + "' not found"));
    }

    private Chat getChat(String chatId) {
        return chatRepository.findById(chatId).orElseThrow(() -> new IllegalArgumentException("Chat with id '" + chatId + "' not found"));
    }

    private Message createMessage(SendMessageRequest request, User user) {
        return Message.builder()
                .id(UUID.randomUUID().toString())
                .chatId(request.getChatId())
                .senderId(user.getId())
                .senderName(user.getUsername())
                .text(request.getMessage())
                .sentAt(LocalDateTime.now())
                .build();
    }


}
