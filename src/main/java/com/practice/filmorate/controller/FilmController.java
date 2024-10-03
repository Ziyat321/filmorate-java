package com.practice.filmorate.controller;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.Film;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int uniqueId = 1;


    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        System.out.println(film);
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new IllegalArgumentException("Дата выхода фильма нереалистична.");
        }
        log.info("Film created: {}", film);
        film.setId(uniqueId++);
        films.put(film.getId(), film);
        return film;

    }

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Finding all films");
        return films.values();
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        System.out.println(film);
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new IllegalArgumentException("Дата выхода фильма нереалистична.");
        }
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильма с данным идентификатором не найден.");
        }
        log.info("Film added: {}", film);
        films.put(film.getId(), film);
        return film;
    }
}