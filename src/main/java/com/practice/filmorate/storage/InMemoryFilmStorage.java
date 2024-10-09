package com.practice.filmorate.storage;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.Film;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int uniqueId = 1;

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Optional<Film> findById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Film create(Film film) {
        film.setId(uniqueId++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм с данным идентификатором не найден.");
        }
        films.put(film.getId(), film);
        return film;
    }
}
