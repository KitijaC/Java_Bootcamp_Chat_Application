package com.chatapplication.chat;

import com.chatapplication.users.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {
    private String message;
}
