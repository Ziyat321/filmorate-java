package com.practice.filmorate;

import com.practice.filmorate.controller.FilmController;
import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.service.FilmService;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.MpaStorage;
import com.practice.filmorate.storage.impl.FilmDbStorage;
import com.practice.filmorate.storage.InMemoryUserStorage;
import com.practice.filmorate.storage.impl.MpaDbStorage;
import com.practice.filmorate.storage.impl.UserDbStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class FilmControllerTest {
    FilmController filmController;

    @BeforeEach
    public void init() {
        filmController = new FilmController(
                new FilmService(
                        new FilmDbStorage(
                                new JdbcTemplate(),
                                new MpaDbStorage(new JdbcTemplate()),
                                new UserDbStorage(new JdbcTemplate())
                        )
                )
        );
    }

    @Test
    public void shouldThrowExceptionWhenReleaseDateIsInvalid() {
        Film film = Film.builder()
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(1890, 1, 1))
                .duration(90)
                .build();
        String expectedMessage = "Дата выхода фильма нереалистична.";

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> filmController.create(film));

        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    public void shouldCreateFilmWhenFilmIsValid() {
        Film film = Film.builder()
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 29))
                .duration(90)
                .build();

        filmController.create(film);
        Film createdFilm = filmController.findAll().stream().toList().get(1);

        assertEquals(film, createdFilm);
    }

    @Test
    public void shouldReturnFilmsList() {
        Film film = Film.builder()
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 29))
                .duration(90)
                .build();
        filmController.create(film);
        Collection<Film> expectedFilms = filmController.findAll();

        Collection<Film> actualFilms = filmController.findAll();

        assertEquals(expectedFilms, actualFilms);
    }

    @Test
    public void shouldThrowExceptionWhenFilmIsAbsent() {
        Film film = Film.builder()
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 29))
                .duration(90)
                .build();
        filmController.create(film);
        Film newFilm = Film.builder()
                .id(5)
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 29))
                .duration(90)
                .build();
        String expectedMessage = "Фильм с данным идентификатором не найден.";

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> filmController.update(newFilm));

        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    public void shouldUpdateFilmWhenFound() {
        Film film = Film.builder()
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 29))
                .duration(90)
                .build();
        filmController.create(film);
        Film newFilm = Film.builder()
                .id(2)
                .name("film2")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 29))
                .duration(90)
                .build();

        filmController.update(newFilm);
        Film updatedFilm = filmController.findAll().stream().toList().get(1);

        assertEquals(newFilm, updatedFilm);
    }
}
