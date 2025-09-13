package com.maisto.ai.tool.action;

/*We can provide ai models tools to take some actions.
* So if we ask the model to take actions it doesn't know how to do,
* it can ask for tool call result.
* The following example defines a simple tool
* */

import org.springframework.ai.tool.annotation.Tool;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TicketCreator {

    private record Ticket(Long id, String assignee, String toDoAction){}
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final ConcurrentHashMap<Long,Ticket> db = new ConcurrentHashMap<>();
    private record TicketResponse(Ticket ticket, String message){}

    @Tool(description = "Create a ticket with toDoAction and assignee")
    public TicketResponse createTicket(String toDoAction, String assignee){
        Long id = idGenerator.getAndIncrement();
        Ticket ticket = new Ticket(id,assignee,toDoAction);
        db.put(id,ticket);
        return new TicketResponse(ticket,"Ticket successfully create and assigned to "+ticket.assignee());
    }

    /*Now we have to provide the tool to the model*/



}
