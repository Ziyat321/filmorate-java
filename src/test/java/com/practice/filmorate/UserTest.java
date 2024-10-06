package com.practice.filmorate;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import com.practice.filmorate.model.User;
import lombok.Builder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


public class UserTest {
    protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldThrowExceptionWhenEmptyEmailGiven() {
        User user = User.builder()
                .email(null)
                .name("name")
                .birthday(LocalDate.now())
                .login("login")
                .build();
        String expected = "Электронная почта не может быть пустой";

        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenEmailNotContainsAt() {
        User user = User.builder()
                .email("user.mail.com")
                .name("name")
                .birthday(LocalDate.now())
                .login("login")
                .build();
        String expected = "Электронная почта не соответствует формату \"user@email.com\"";

        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenLoginIsBlank() {
        User user = User.builder()
                .email("user@mail.com")
                .name("name")
                .birthday(LocalDate.now())
                .login("")
                .build();
        String expected = "Логин не может быть пустым";

        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenLoginContainsSpaces() {
        User user = User.builder()
                .email("user@mail.com")
                .name("name")
                .birthday(LocalDate.now())
                .login("log in")
                .build();
        String expected = "Логин не может содержать пробелы";

        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenBirthdayIsInFuture() {
        User user = User.builder()
                .email("user@mail.com")
                .name("name")
                .birthday(LocalDate.of(2030, 1, 15))
                .login("login")
                .build();
        String expected = "Дата рождения не может быть в будущем";

        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    protected String validateAndGetFirstMessageTemplate(User obj) {
        return validator.validate(obj).stream()
                .findFirst()
                .orElseThrow()
                .getConstraintDescriptor()
                .getMessageTemplate();
    }
}
