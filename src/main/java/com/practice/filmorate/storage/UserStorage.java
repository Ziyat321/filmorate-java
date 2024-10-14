package com.practice.filmorate.storage;

import com.practice.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserStorage {
    List<User> findAll();

    User findById(int id);

    User create(User user);

    User update(User user);
}
