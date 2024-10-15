package com.practice.filmorate.storage.impl;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.exception.ValidationException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;

    private final static String SELECT = """
            select f.id           as film_id,
                   f.name         as film,
                   f.description  as description,
                   f.release_date as release_date,
                   f.duration     as duration,
                   mpa.id         as mpa_id,
                   mpa.name       as mpa,
                   g.id           as genre_id,
                   g.name         as genre,
                   fl.user_id     as user_id
            from films as f
                     inner join mpa on f.mpa_id = mpa.id
                     left join films_genres as fg
                                on f.id = fg.film_id
                     left join genres as g
                                on fg.genre_id = g.id
                     left join films_likes as fl on f.id = fl.film_id
            order by film_id, genre_id, user_id;
            """;

    private final static String SELECT_BY_ID = """
            select f.id           as film_id,
                   f.name         as film,
                   f.description  as description,
                   f.release_date as release_date,
                   f.duration     as duration,
                   mpa.id         as mpa_id,
                   mpa.name       as mpa,
                   g.id           as genre_id,
                   g.name         as genre,
                   fl.user_id     as user_id
            from films as f
                     inner join mpa on f.mpa_id = mpa.id
                     left join films_genres as fg
                                on f.id = fg.film_id
                     left join genres as g
                                on fg.genre_id = g.id
                     left join films_likes as fl on f.id = fl.film_id
            where fg.film_id=?
            order by film_id, genre_id, user_id;
            """;


    private void filmSetInfo(SqlRowSet rowSet, Film film) {
        int filmId = rowSet.getInt("film_id");
        String filmName = rowSet.getString("film");
        String description = rowSet.getString("description");
        LocalDate releaseDate = rowSet.getDate("release_date").toLocalDate();
        int duration = rowSet.getInt("duration");
        int mpaId = rowSet.getInt("mpa_id");
        String mpaName = rowSet.getString("mpa");
        int genreId = rowSet.getInt("genre_id");
        String genre = rowSet.getString("genre");
        int userId = rowSet.getInt("user_id");

        film.setId(filmId);
        film.setName(filmName);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration);
        film.setMpa(new Mpa(mpaId, mpaName));
        film.getGenres().add(new Genre(genreId, genre));
        film.getLikes().add(userId);
    }

    @Override
    public List<Film> findAll() {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SELECT);

        List<Film> films = new ArrayList<>();

        int filmIdPrev = 0;
        Film film = new Film(); //film.getId() = 0
        while (rowSet.next()) {
            int filmId = rowSet.getInt("film_id");
            if (filmId != filmIdPrev) {   // забиваем инфу для нового фильма
                if (film.getId() != 0) {
                    films.add(film);
                }
                film = new Film();
                filmSetInfo(rowSet, film);

                filmIdPrev = filmId;
            } else {                                         // добиваем инфу по жанрам и лайкам для уже созданного фильма
                int genreId = rowSet.getInt("genre_id");
                String genre = rowSet.getString("genre");
                int userId = rowSet.getInt("user_id");
                film.getGenres().add(new Genre(genreId, genre));
                film.getLikes().add(userId);
            }
        }
        films.add(film);
        return films;
    }

    @Override
    public Film findById(int id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SELECT_BY_ID);

        if (!rowSet.next()) {
            throw new NotFoundException("Фильма с данным " + id + " не существует");
        }

        int filmIdPrev = 0;
        Film film = new Film();
        while (rowSet.next()) {
            int filmId = rowSet.getInt("film_id");
            if (filmId != filmIdPrev) {
                filmSetInfo(rowSet, film);

                filmIdPrev = filmId;
            } else {
                int genreId = rowSet.getInt("genre_id");
                String genre = rowSet.getString("genre");
                int userId = rowSet.getInt("user_id");
                film.getGenres().add(new Genre(genreId, genre));
                film.getLikes().add(userId);
            }
        }
        return film;
    }

    @Transactional
    @Override
    public Film create(Film film) {
        mpaStorage.findById(film.getMpa().getId())
                .orElseThrow(() -> new ValidationException("..."));

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> params = Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration(),
                "mpa_id", film.getMpa().getId()
        );

        int id = insert.executeAndReturnKey(params).intValue();
        film.setId(id);

        Set<Genre> filmGenres = film.getGenres();
        String sql = """
                insert into films_genres(film_id, genre_id)
                values (?, ?)
                """;
        for (Genre filmGenre : filmGenres) {
            jdbcTemplate.update(sql, id, filmGenre.getId());
        }
        return film;
    }

    @Override
    public Film update(Film film) {
        String sql = """
                update films
                set name = ?,
                    description = ?,
                    release_date = ?,
                    duration = ?, 
                    mpa_id = ?
                where id = ?
                """;

        int result = jdbcTemplate.update(sql, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());

        if(result == 0) throw new NotFoundException("...");
        return film;
    }
}
