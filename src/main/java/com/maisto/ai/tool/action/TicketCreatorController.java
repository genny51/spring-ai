package com.maisto.ai.tool.action;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TicketCreatorController {
    private ChatClient chatClient;

    public TicketCreatorController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @GetMapping("/ticket")
    public String createTask(@RequestParam String message) {
        return chatClient.prompt()
                .tools(new TicketCreator()) //providing tool to model
                .user(message) //*1
                .call()
                .content();
    }

    /*
        *1:
      *If i ask for 'Create a ticket with todoaction=do something and assignee=Paolo' the model will look
      *to the tool passed in tools method and asks for method call with specific input parameters.Our ChatClient
      *will call internal specific method and send result to model.
    */

}
