package com.practice.filmorate.storage;

import com.practice.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {
    Collection<Film> findAll();

    Optional<Film> findById(int id);

    Film create(Film user);

    Film update(Film user);
}
