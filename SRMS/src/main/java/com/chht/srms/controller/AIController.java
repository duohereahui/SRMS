package com.chht.srms.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {

    private final ChatClient chatClient;


    private final InMemoryChatMemory chatMemory = new InMemoryChatMemory();

    @GetMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<String> chat(
            @RequestParam(value = "message") String message,
            @RequestParam(value = "chatId", defaultValue = "379268") String chatId) {

        // 1. 异步获取历史消息
        CompletableFuture.runAsync(() -> {
            List<Message> history = chatMemory.get(chatId, 10);
            log.info("获取到历史消息: {} 条", history.size());
        });

        // 2. 创建流式响应
        Flux<String> responseStream = chatClient.prompt()
                .user(message)
                .stream()
                .content();

        // 3. 异步处理完整响应的保存
        StringBuilder fullResponse = new StringBuilder();
        // 累积响应内容
        return responseStream
                .doOnNext(fullResponse::append)
                .doOnComplete(() -> {
                    // 在流结束时异步保存对话历史
                    CompletableFuture.runAsync(() -> {
                        try {
                            // 保存对话
                            List<Message> messages = Arrays.asList(
                                    new UserMessage(message),
                                    new AssistantMessage(fullResponse.toString())
                            );
                            chatMemory.add(chatId, messages);
                            log.info("对话历史保存成功 - chatId: {}", chatId);
                        } catch (Exception e) {
                            log.error("保存对话历史失败 - chatId: {}", chatId, e);
                        }
                    });
                });
    }
}



