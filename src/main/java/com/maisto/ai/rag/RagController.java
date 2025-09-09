package com.maisto.ai.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag")
public class RagController {

    private ChatClient chatClient;

    public RagController(ChatClient.Builder builder, VectorStore vectorStore){
        this.chatClient = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore)) //*1
                .build();
    }

    @GetMapping("/models")
    public Models getWithRagModels(@RequestParam(defaultValue = "Give me all models of Openai along with their context window") String message){
        return chatClient
                .prompt()
                .user(message)
                .call()
                .entity(Models.class);
    }

    /*
    *1:
        every prompt sended with this chatclient will be augmented. QuestionAnswerAdvisor will query vector database to search similar informations and
        will inject them as context informations in the prompt. It will implement a similarity search by default

    */





}
