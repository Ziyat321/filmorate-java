package com.practice.filmorate.service;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User create(User user){
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    public User update(User user){
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.update(user);
    }

    public User findById(int id){
        return userStorage.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + id + " не найден"));
    }

    public Collection<User> findAll(){
        return userStorage.findAll();
    }

    public void addfriend(int userId, int otherUserId){
        //TODO
        User user = ;
        user.getFriends().add(otherUserId);
    }

    public void removeFriend(int userId, int otherUserId){
        //TODO

    }

    public List<User> findAllFriends(int userId){
        //TODO
        User user = ;

        return user.getFriends().stream()
                .map(friendId -> findById(friendId))
                .toList();
    }
}
