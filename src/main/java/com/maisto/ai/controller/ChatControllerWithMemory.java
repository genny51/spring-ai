package com.maisto.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*What's the difference with ChatController ?
* LLM like all http calls are stateless. So if we take a conversation with our model of this type:
* "Hi my name is Raffaele " R-> Hi Raffaele, how can i support you ?
* "Hi , what's my name ? R-> I haven't access to this information.
*
* So if we want to take a really conversation with our model, we have to handle the memory
*
* */
@RestController
public class ChatControllerWithMemory {

    private final ChatClient chatClient;

    public ChatControllerWithMemory(ChatClient.Builder builder, ChatMemory chatMemory){
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @GetMapping("/memory")
    public String memory(@RequestParam String text){

        return chatClient.prompt()
                .user(text)
                .call()
                .content();
    }

}
