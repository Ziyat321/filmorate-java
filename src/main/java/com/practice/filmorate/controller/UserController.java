package com.practice.filmorate.controller;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int uniqueId = 1;


    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Creating user: {}", user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(uniqueId++);
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public Collection<User> findAll() {
        log.info("Finding all users");
        return users.values();
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователя с данным идентификатором не существует.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.info("Creating new user: {}", user);
        users.put(user.getId(), user);
        return user;
    }
}