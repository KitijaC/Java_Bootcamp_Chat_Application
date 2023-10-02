package com.chatapplication.chat;

import com.chatapplication.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    private final ChatRepository chatRepository;
    private final UserService userService;

    @Autowired
    public ChatController(ChatRepository chatRepository, UserService userService){
        this.chatRepository = chatRepository;
        this.userService = userService;
    }
    @GetMapping("/chat-room")
    public String displayChatRoom(@CookieValue(value = "loggedInUserId", defaultValue = "") String userId, Model model) {
        try {
            if (userId.isBlank()) throw new RuntimeException("User session not found or expired. Log in to continue");
            // we need services to be able to share functionalities between controllers like in this case
            model.addAttribute("user", this.userService.getUserById(Long.parseLong(userId)));
            model.addAttribute("chatList", this.chatRepository.findAll());
            return "chat-room";
        } catch (Exception exception) {
            return "redirect:/login?status=CHAT_ROOM_ERROR&message=" + exception.getMessage();
        }

    }
}
