package com.chatapplication.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    @GetMapping("/chat-room")
    public String displayChatRoom() {
        return "chat-room";
    }
}
