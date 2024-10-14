package com.practice.filmorate.service;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.exception.ValidationException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.storage.FilmDbStorage;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmDbStorage filmDbStorage;
    private final UserStorage userStorage;

    public Film create(Film film) {
        if (film.getReleaseDate().compareTo(new Date(1895, 12, 28)) < 0) {
            throw new ValidationException("Дата выхода фильма нереалистична.");
        }

        return filmDbStorage.create(film);
    }

    public Film update(Film film) {
        if (film.getReleaseDate().compareTo(new Date(1895, 12, 28)) < 0) {
            throw new ValidationException("Дата выхода фильма нереалистична.");
        }
        return filmDbStorage.update(film);
    }

    public Film findById(int id) {
        return filmDbStorage.findById(id);
    }

    public List<Film> findAll() {
        return filmDbStorage.findAll();
    }

    public void likeFilm(int filmId, int userId) {
        Film film = findById(filmId);
        userStorage.findById(userId);
        film.getLikes().add(userId);
    }

    public void unlikeFilm(int filmId, int userId) {
        Film film = findById(filmId);
        userStorage.findById(userId);
        film.getLikes().remove(userId);
    }

    public List<Film> popularFilms(Integer count) {

        return filmDbStorage.findAll().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getLikes().size(), o1.getLikes().size()))
                .limit(count)
                .toList();
    }
}
