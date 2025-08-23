package com.maisto.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
@RestController
public class ImageDetection {

    private final ChatClient chatClient;

    @Value("classpath:/images/home.jpg")
    private Resource sampleImage;

    public ImageDetection(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/image-to-text")
    public String imageToText(){
        return chatClient.prompt()
                .user(u -> {
                    u.text("Descrivimi l'immagine che ti ho allegato");
                    u.media(MediaType.IMAGE_JPEG,sampleImage);
                })
                .call()
                .content();
    }

}
