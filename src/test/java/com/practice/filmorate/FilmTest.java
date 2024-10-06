package com.practice.filmorate;

import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class FilmTest {
    protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void shouldThrowExceptionWhenFilmIsNull() {
        Film film = Film.builder()
                .name(null)
                .description("cool")
                .releaseDate(LocalDate.of(2001, 2, 20))
                .duration(90)
                .build();

        String expected = "Название фильма не может быть пустым";

        String actual = validateAndGetFirstMessageTemplate(film);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowExceptionWhenDescriptionSizeIsGreaterThan200() {
        Film film = Film.builder()
                .name("film")
                .description("cool".repeat(200))
                .releaseDate(LocalDate.of(2001, 2, 20))
                .duration(90)
                .build();

        String expected = "Максимальная длинна сообщения 200 символов";

        String actual = validateAndGetFirstMessageTemplate(film);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowExceptionWhenDurationIsNotPositive() {
        Film film = Film.builder()
                .name("film")
                .description("cool")
                .releaseDate(LocalDate.of(2001, 2, 20))
                .duration(0)
                .build();

        String expected = "Продолжительность фильма должна быть положительной";

        String actual = validateAndGetFirstMessageTemplate(film);

        Assertions.assertEquals(expected, actual);
    }

    protected String validateAndGetFirstMessageTemplate(Film obj) {
        return validator.validate(obj).stream()
                .findFirst()
                .orElseThrow()
                .getConstraintDescriptor()
                .getMessageTemplate();
    }
}
