package com.practice.filmorate;

import com.practice.filmorate.controller.UserController;
import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.service.UserService;
import com.practice.filmorate.storage.InMemoryUserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


class UserControllerTest {
    UserController userController;

    @BeforeEach
    void init() {
        userController = new UserController(new UserService(new InMemoryUserStorage()));
    }

    @Test
    void setNameAsLoginWhenNameIsBlank() {
        User user = User.builder()
                .email("email@mail.com")
                .name("")
                .birthday(LocalDate.of(1990, 1, 1))
                .login("login")
                .build();
        String expectedName = user.getLogin();

        String actualName = userController.create(user).getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void shouldCreateUserWhenUserIsValid() {
        User user = User.builder()
                .email("email@mail.com")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .login("login")
                .build();

        userController.create(user);
        User createdUser = userController.getUsers().get(1);

        assertEquals(user, createdUser);
    }

    @Test
    void shouldReturnUsersList() {
        User user = User.builder()
                .email("email@mail.com")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .login("login")
                .build();
        userController.create(user);
        Collection<User> expectedUsers = userController.getUsers().values();

        Collection<User> actualUsers = userController.findAll();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void shouldThrowExceptionWhenUserIsAbsent() {
        User user = User.builder()
                .email("email@mail.com")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .login("login")
                .build();
        userController.create(user);
        User newUser = User.builder()
                .id(2)
                .email("email@mail.com")
                .name("name2")
                .birthday(LocalDate.of(1990, 1, 1))
                .login("login")
                .build();
        String expectedMessage = "Пользователя с данным идентификатором не существует.";

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> userController.update(newUser));

        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    void shouldUpdateUserWhenFound() {
        User user = User.builder()
                .email("email@mail.com")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .login("login")
                .build();
        userController.create(user);
        User newUser = User.builder()
                .id(1)
                .email("email@mail.com")
                .name("name2")
                .birthday(LocalDate.of(1990, 1, 1))
                .login("login")
                .build();

        userController.update(newUser);
        User updatedUser = userController.getUsers().get(1);

        assertEquals(newUser, updatedUser);
    }
}