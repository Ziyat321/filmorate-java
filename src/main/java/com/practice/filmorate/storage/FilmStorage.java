package com.practice.filmorate.storage;

import com.practice.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    List<Film> findAll();

    Film findById(int id);

    Film create(Film film);

    Film update(Film film);

    List<Film> popularFilms(Integer count);

    void likeFilm(int filmId, int userId);

    void unlikeFilm(int filmId, int userId);
}
