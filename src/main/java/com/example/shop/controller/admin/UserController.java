package com.example.shop.controller.admin;

import com.example.shop.respository.UserRepository;
import com.example.shop.service.UserService;
import com.example.shop.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController {
    private UserService userService;
    private UserRepository userRepository;

    public UserController(UserService userService,UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @GetMapping("")
    public String getAllUser(Model model){
        List<User> user = userRepository.findAll();
        model.addAttribute("user", user );
        return "admin/user/User";
    }
    @GetMapping("addUser")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        return "admin/user/addUser";
    }
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("User") User User){
        userService.saveUser(User);
        return "redirect:/admin/users";
    }
    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable(value = "id") long id, Model model){
        User User = userService.getUserById(id);
        model.addAttribute("User", User);
        return "admin/user/updateUser";
    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id){
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    ///API:

    /*@PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long userId) {
        return new ResponseEntity<User>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long userId, @RequestBody User user) {
        return new ResponseEntity<User>(userService.updateUser(user, userId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<String>("User deleted successfully", HttpStatus.OK);
    }*/
}
