package com.chatapplication.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired // please spring provide us with a copy of all dependencies we need
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserEntity userEntity) throws Exception {
        this.userRepository.save(userEntity);
    }

    public UserEntity verifyUser(String username, String password) throws Exception {
        UserEntity user = this.userRepository.findByUsernameAndPassword(username, password);
        System.out.println(user);
        return user;
    }
}
