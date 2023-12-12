package com.example.springbootmicroservice3.controller;


import com.example.springbootmicroservice3.model.ChatMessage;
import com.example.springbootmicroservice3.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping()
@Controller
public class ChatController {


    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessagePojo) {

        String recipientId = chatMessagePojo.getRec();
        String senderId = chatMessagePojo.getSender();
        chatMessageRepository.save(chatMessagePojo);

        // Send the message to the specified recipient
        messagingTemplate.convertAndSend("/topic/"+recipientId, chatMessagePojo);
        messagingTemplate.convertAndSend("/topic/"+senderId, chatMessagePojo);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessagePojo, SimpMessageHeaderAccessor headerAccessor) {

        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessagePojo.getSender());
        return chatMessagePojo;
    }
    @GetMapping("/conversation/{senderId}/{recipientId}")
    public List<ChatMessage> getConversation(@PathVariable("senderId") String senderId, @PathVariable("recipientId") String recipientId) {
        System.out.println(senderId+"****"+recipientId);
        return chatMessageRepository.findAll();
    }

    @PostMapping("/conversation")
    public ChatMessage addMessage( @RequestBody ChatMessage chatMessage) {

        return chatMessageRepository.save(chatMessage);
    }
}


