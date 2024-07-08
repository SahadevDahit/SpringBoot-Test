package com.example.test.controllers;

import com.example.test.entities.UserEntity;
import com.example.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String Users_Home(){
        return userService.defaultUserService();
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody UserEntity user){
       return userService.signup(user);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getByID(@PathVariable String id){
        return userService.getUserDetailsById(id);
    }

    @GetMapping("/getByUserName/{username}")
    public ResponseEntity<UserEntity> getByUserName(@PathVariable String username){
        return userService.getByUserName(username);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        return userService.deleteUserById(id);
    }




}
