package com.practice.filmorate;

import com.practice.filmorate.controller.FilmController;
import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


public class FilmControllerTest {
    FilmController filmController;

    @BeforeEach
    public void init() {
        filmController = new FilmController();
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
    public void shouldCreateFilmWhenFilmIsValid(){
        Film film = Film.builder()
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 29))
                .duration(90)
                .build();

        filmController.create(film);
        Film createdFilm = filmController.getFilms().get(1);

        assertEquals(film, createdFilm);
    }

    @Test
    public void shouldReturnFilmsList(){
        Film film = Film.builder()
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 29))
                .duration(90)
                .build();
        filmController.create(film);
        Collection<Film> expectedFilms = filmController.getFilms().values();

        Collection<Film> actualFilms = filmController.findAll();

        assertEquals(expectedFilms, actualFilms);
    }

    @Test
    public void shouldThrowExceptionWhenFilmIsAbsent(){
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
        String expectedMessage = "Фильм с данным идентификатором не найден.";

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> filmController.update(newFilm));

        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    public void shouldUpdateFilmWhenFound(){
        Film film = Film.builder()
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 29))
                .duration(90)
                .build();
        filmController.create(film);
        Film newFilm = Film.builder()
                .id(1)
                .name("film2")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 29))
                .duration(90)
                .build();

        filmController.update(newFilm);
        Film updatedFilm = filmController.getFilms().get(1);

        assertEquals(newFilm, updatedFilm);
    }
}
