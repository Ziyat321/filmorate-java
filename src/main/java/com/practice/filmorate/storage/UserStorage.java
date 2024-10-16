package com.practice.filmorate.storage;

import com.practice.filmorate.model.User;


import java.util.List;


public interface UserStorage {
    List<User> findAll();

    User findById(int id);

    User create(User user);

    User update(User user);

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    List<User> findAllFriends(int userId);

    List<User> findAllCommonFriends(int userId, int otherUserId);
}
