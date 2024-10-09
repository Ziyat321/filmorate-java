package com.practice.filmorate.controller;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.service.UserService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
@Builder
@Getter
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Creating user: {}", user);
        return userService.create(user);
    }

    @GetMapping
    public Collection<User> findAll() {
        log.info("Finding all users");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable int id) {
        log.info("Finding user by id: {}", id);
        return userService.findById(id);
    }


    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Creating new user: {}", user);
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId){
        log.info("Adding friend to user: {}", id);
        userService.addfriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> findAllFriends(@PathVariable int id){
        log.info("Finding all friends for user: {}", id);
        return userService.findAllFriends(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id,  @PathVariable int friendId) {
        log.info("Deleting user: {}", id);
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Finding common friends for user: {}", id);
        return userService.findCommonFriends(id, otherId);
    }
}