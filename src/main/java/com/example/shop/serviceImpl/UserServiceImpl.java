package com.example.shop.serviceImpl;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.User;
import com.example.shop.respository.UserRepository;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    @Override
    public User getUserById(long id) {
        /*Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }else {
            throw new ResourceNotFoundException("User","Id",id);
        }*/
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User","Id",id));
    }
    @Override
    public User updateUser(User user, long id) {
        User existingUser = userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User","Id",id));
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        userRepository.save(existingUser);
        return existingUser;
    }
    @Override
    public void deleteUser(long id) {
        userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User","Id",id));
        userRepository.deleteById(id);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
