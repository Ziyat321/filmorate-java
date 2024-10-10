package com.practice.filmorate.service;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.exception.ValidationException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film create(Film film){
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата выхода фильма нереалистична.");
        }

        return filmStorage.create(film);
    }

    public Film update(Film film){
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата выхода фильма нереалистична.");
        }
        return filmStorage.update(film);
    }

    public Film findById(int id){
        return filmStorage.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + id + " не найден"));
    }

    public Collection<Film> findAll(){
        return filmStorage.findAll();
    }

    public void likeFilm(int filmId, int userId){
        Film film = findById(filmId);
        userStorage.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));
        film.getLikes().add(userId);
    }

    public void unlikeFilm(int filmId, int userId){
        Film film = findById(filmId);
        userStorage.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));
        film.getLikes().remove(userId);
    }

    public List<Film> popularFilms(Integer count){

        return filmStorage.findAll().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getLikes().size(), o1.getLikes().size()))
                .limit(count)
                .toList();
    }
}
