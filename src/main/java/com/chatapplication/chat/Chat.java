package com.chatapplication.chat;

import com.chatapplication.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id @GeneratedValue
    private long id;
    @ManyToOne // can be a different way to connect the tables oneToMany is another option
    private UserEntity sender;
    private String message;
    private Timestamp createdAt;

    @PrePersist // this will allow the method below to be called
    // before a chat object is saved to database
    public void beforeSave(){
        this.createdAt = new Timestamp(System.currentTimeMillis());
        // do other things
    }
}
