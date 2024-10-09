package com.practice.filmorate.service;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.FilmStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

    public Film create(Film film){
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new IllegalArgumentException("Дата выхода фильма нереалистична.");
        }
        filmStorage.create(film);
        return filmStorage.create(film);
    }

    public Film update(Film film){
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new IllegalArgumentException("Дата выхода фильма нереалистична.");
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
        film.getLikes().add(userId);
    }

    public void unlikeFilm(int filmId, int userId){
        Film film = findById(filmId);
        film.getLikes().remove(userId);
    }

    public List<Film> popularFilms(Integer count){
        final int numberOfFilms = count == null ? 10 : count;
        List<Film> films = List.copyOf(filmStorage.findAll());
        if(films.size() <= numberOfFilms){
            return films;
        } else {
            List<Film> result = films.subList(0, numberOfFilms);
            for (int i = numberOfFilms; i < films.size(); i++) {
                int minLikes = result.stream()
                        .mapToInt(film ->film.getLikes().size())
                        .min().orElse(result.get(0).getLikes().size());
                if(films.get(i).getLikes().size() > minLikes){
                    for(int j = numberOfFilms - 1; j >= 0; j--){
                        if(result.get(j).getLikes().size() == minLikes){
                            result.add(j, films.get(i));
                            break;
                        }
                    }
                }
            }
            return result;
        }
    }
}
