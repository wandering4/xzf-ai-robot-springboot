package com.sankuai.ai.robot.config;

import com.sankuai.ai.robot.advisor.MyLoggerAdvisor;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Resource
    private ChatMemory chatMemory;

    /**
     * 初始化 ChatClient 客户端
     * @param chatModel
     * @return
     */
    @Bean
    public ChatClient chatClient(DeepSeekChatModel chatModel) {
        return ChatClient
                .builder(chatModel)
                .defaultSystem("请你扮演一名Java工程师")
                .defaultAdvisors(new SimpleLoggerAdvisor(),
//                        new MyLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                ) // 添加 Spring AI 内置的日志记录功能
                .build();
    }



}
