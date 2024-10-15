package com.practice.filmorate.storage;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.User;
import org.springframework.stereotype.Component;

import java.util.*;

public abstract class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int uniqueId = 1;

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public User findById(int id) {
        return users.values().stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователя с данным " + id + " не существует"));
    }

    @Override
    public User create(User user) {
        user.setId(uniqueId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователя с данным идентификатором не существует.");
        }
        users.put(user.getId(), user);
        return user;
    }
}
