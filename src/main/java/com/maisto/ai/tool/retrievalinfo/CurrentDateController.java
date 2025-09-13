package com.maisto.ai.tool.retrievalinfo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/*
    AI models don't have access to real-time informations.
    We can provide them tools to be called either for retrieving information
    and either for taking actions.
    The following example describes how use tool calling for retrieving informations.
*/

@RestController
public class CurrentDateController {

    private ChatClient chatClient;

    public CurrentDateController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @GetMapping("/currentTime")
    public String toDayDate(){
        return chatClient.prompt()
                .tools(new TimeUtils()) //we provide the models the tool
                .user("What day is tomorrow?") //*1
                .call()
                .content();
    }

    /*
        *1:
    * When the model receives the question, to respond it needs of current date.
    * It saw our tool available and asks for its result.Internally ChatClient
    * handles model request, calls tool and sends to the model the result. The
    * model will use the response as context informations to give the response.
    * */
}
