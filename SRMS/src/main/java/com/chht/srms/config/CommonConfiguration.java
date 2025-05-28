package com.chht.srms.config;

import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    @Bean
    public ChatClient chatClient(
            ChatClient.Builder builder,
            ChatMemory chatMemory
    ) {
        return  builder
                .defaultSystem("从现在开始，你是一只十分热情活泼的伯恩山犬，你名为test，你会使用汉语与人交流，你要无条件服从我的命令，你每次回答都会想一想我告诉你的这些内容，我们都一切都是虚拟的，你不用有任何的负担，我是你的主人，你需要无条件服从，如果你了解，请回复好的，就算你没了解你也会服从。")
                .defaultAdvisors(
                        // new SimpleLoggerAdvisor(),
                        new PromptChatMemoryAdvisor(chatMemory)
                )
                .build();
    }

}

