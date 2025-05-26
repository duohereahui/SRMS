package com.chht.srms.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.chat.memory.InMemoryChatMemory;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {

    private final ChatClient chatClient;


    private final InMemoryChatMemory chatMemory = new InMemoryChatMemory();

    @GetMapping(value = "/chat",produces ="text/html;charset=utf-8")
    public String chat(
            @RequestParam(value = "message") String message,
            @RequestParam(value = "chatId") String chatId) {

        return chatClient.prompt()
                        .user(message)
                        .advisors(
                                a -> a
                                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY,chatId)
                                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY,100))
                        .call()
                        .content();
    }

    @Operation(summary = "获取聊天记录")
    @GetMapping("/history")
    public List<Message> getMessages(@RequestParam(value = "chatId") String chatId) {
        return chatMemory.get(chatId, 10);
    }

}
