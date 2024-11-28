package org.messager.writerapp.api;

import lombok.RequiredArgsConstructor;
import org.messager.writerapp.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WriterController {

    private final WriterService writerService;

    @PostMapping("/user")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(writerService.createUser(request));
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> createChat(@RequestBody CreateChatRequest request, @RequestHeader("userId") String userId) {
        return ResponseEntity.ok(writerService.createChat(request, userId));
    }

    @DeleteMapping("/chat/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable String chatId, @RequestHeader("userId") String userId) {
        writerService.deleteChat(chatId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/chat/{chatId}/join")
    public ResponseEntity<ChatResponse> joinChat(@PathVariable String chatId, @RequestHeader("userId") String userId) {
        return ResponseEntity.ok(writerService.joinChat(chatId, userId));
    }

    @PostMapping("/chat/{chatId}/leave")
    public ResponseEntity<Void> leaveChat(@PathVariable String chatId, @RequestHeader("userId") String userId) {
        writerService.leaveChat(chatId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/message")
    public ResponseEntity<Void> sendMessage(@RequestBody SendMessageRequest request, @RequestHeader("userId") String userId) {
        writerService.sendMessage(request, userId);
        return ResponseEntity.ok().build();
    }
}
