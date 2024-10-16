package com.practice.filmorate.service;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.exception.ValidationException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    public User update(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.update(user);
    }

    public User findById(int id) {
        return userStorage.findById(id);
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public void addFriend(int userId, int friendId) {
        if (userId == friendId) {
            throw new ValidationException("Нельзя добавить самого себя в друзья");
        }
        userStorage.addFriend(userId, friendId);
    }

    public void removeFriend(int userId, int friendId) {
        userStorage.removeFriend(userId, friendId);
    }

    public List<User> findAllFriends(int userId) {
        return userStorage.findAllFriends(userId);
    }

    public List<User> findAllCommonFriends(int userId, int otherUserId) {
        return userStorage.findAllCommonFriends(userId, otherUserId);
    }
}
