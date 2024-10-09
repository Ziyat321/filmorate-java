package com.practice.filmorate.controller;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.User;
import com.practice.filmorate.service.FilmService;
import com.practice.filmorate.service.UserService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
@Builder
@Getter
public class FilmController {
    private final FilmService filmService;


    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Film created: {}", film);
        return filmService.create(film);
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Finding all films");
        return filmService.findAll();
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Film added: {}", film);
        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film findById(@PathVariable int id) {
        log.info("Finding film by id: {}", id);
        return filmService.findById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("Liking film with id: {} and user id: {}", id, userId);
        filmService.likeFilm(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void unlikeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("Unliking film with id: {} and user id: {}", id, userId);
    }

    @GetMapping("/popular")
    public List<Film> popularFilms(@RequestParam(required = false) Integer count) {
        log.info("Getting {} popular films", count);
        return filmService.popularFilms(count);
    }
}