package com.maisto.ai.controller;

import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
/*Generation of images with ai*/
@RestController
public class ImageGenerator {

    private final OpenAiImageModel openAiImageModel;

    public ImageGenerator(OpenAiImageModel openAiImageModel){
        this.openAiImageModel = openAiImageModel;
    }

    @GetMapping("/image")
    public ResponseEntity<Map<String, String>> image(@RequestParam(defaultValue = "Un tramonto stupendo sull'isola di Lampedusa") String prompt){

        ImageOptions imageOptions = OpenAiImageOptions.builder()
                .model("dall-e-3")
                .height(1024)
                .width(1024)
                .quality("hd")
                .style("vivid")
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(prompt, imageOptions);
        ImageResponse imageResponse = openAiImageModel.call(imagePrompt);

        String url = imageResponse.getResult().getOutput().getUrl();

        return ResponseEntity.ok(Map.of(
                "prompt", prompt
                ,"imageUrl",url
        ));

    }
}
