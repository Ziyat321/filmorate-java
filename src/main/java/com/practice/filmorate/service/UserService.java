package com.practice.filmorate.service;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.exception.ValidationException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    public void addfriend(int userId, int otherUserId) {
        if (userId == otherUserId) {
            throw new ValidationException("Нельзя добавить самого себя в друзья");
        }
        User user = findById(userId);
        User otherUser = findById(otherUserId);
        user.getFriends().add(otherUserId);
        otherUser.getFriends().add(userId);
    }

    public void removeFriend(int userId, int otherUserId) {
        User user = findById(userId);
        User otherUser = findById(otherUserId);
        user.getFriends().remove(otherUserId);
        otherUser.getFriends().remove(userId);
    }

    public List<User> findAllFriends(int userId) {
        User user = findById(userId);

        return user.getFriends().stream()
                .map(this::findById)
                .toList();
    }

    public List<User> findCommonFriends(int userId, int otherUserId) {
        User user = findById(userId);
        User otherUser = findById(otherUserId);
        Set<Integer> commonFriends = user.getFriends();
        commonFriends.retainAll(otherUser.getFriends());

        return commonFriends.stream()
                .map(this::findById)
                .toList();
    }
}
