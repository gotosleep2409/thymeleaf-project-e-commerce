package com.example.shop.service;

import com.example.shop.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    User saveUser(User user);
    List<User> getAllUser();
    User getUserById(long id);
    User updateUser(User user, long id);
    void deleteUser(long id);
    User findByUsername(String username);
}
