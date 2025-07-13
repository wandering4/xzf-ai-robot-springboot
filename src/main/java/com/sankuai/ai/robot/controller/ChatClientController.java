package com.sankuai.ai.robot.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Version: v1.0.0
 * @Description: Chat Client 客户端
 **/
@RestController
@RequestMapping("/v2/ai")
public class ChatClientController {

    @Resource
    private ChatClient chatClient;

    /**
     * 普通对话
     * @param message
     * @return
     */
    @GetMapping("/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "你是谁？") String message, @RequestParam(value = "chatId") String chatId) {
        // 一次性返回结果
        return chatClient.prompt()
                .user(message)
                .advisors(a->a.param(ChatMemory.CONVERSATION_ID, chatId))
                .call()
                .content();
    }


    /**
     * 流式对话
     * @param message
     * @retur return chatClient.prompt()
     * .user(message) // 提示词
     * .stream() // 流式输出
     * .content();n
     */
    @GetMapping(value = "/generateStream", produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "你是谁？") String message, @RequestParam(value = "chatId") String chatId) {
        return chatClient.prompt()
                .system("请你扮演一名犬小哈 Java 项目实战专栏的客服人员")
                .user(message) // 提示词
                .advisors(a->a.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream() // 流式输出
                .content();
    }

}
