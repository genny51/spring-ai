package com.maisto.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    private ChatClient chatClient;

    public ChatController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    /*
        A simple example to call ai api
    */
    @GetMapping("/chat")
    public String chat(){
       return chatClient.prompt()
                .user("Tell me something about java of interesting")
                .call()//it's a blocking call
                .content();// it returns only the content of response
    }

    /*
        If we were asking for a large response,
        it would not be a good experience the attendance of the entire response.
        With the below implementation we receive response as the comes
    */
    @GetMapping("/stream")
    public Flux<String> stream(){
        return chatClient.prompt()
                .user("Give me ten places where i must visit in Las Vegas")
                .stream()//it returns response as it arrives from the ai api
                .content();
    }

    /*
        We provide an example that return a ChatResponse type
    */
    @GetMapping("/joke")
    public ChatResponse joke(){
        return chatClient.prompt()
                .user("Say me something of joke about dogs")
                .call()
                .chatResponse();// it returns the json containing not only the content but also the metadata
    }
}
