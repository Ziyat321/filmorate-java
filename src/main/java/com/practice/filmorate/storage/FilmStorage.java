package com.practice.filmorate.storage;

import com.practice.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    List<Film> findAll();

    Film findById(int id);

    Film create(Film user);

    Film update(Film user);
}
