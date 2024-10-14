package com.practice.filmorate.storage;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Mpa;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage{
    private final JdbcTemplate jdbcTemplate;

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
                   u.id           as user_id,
                   u.name         as user_name
            from films as f
                     inner join films_genres as fg
                                on f.id = fg.film_id
                     inner join genres as g
                                on fg.genre_id = g.id
                     inner join mpa on f.mpa_id = mpa.id
                     inner join films_likes as fl on f.id = fl.film_id
                     inner join users as u on fl.user_id = u.id
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
                   u.id           as user_id,
                   u.name         as user_name
            from films as f
                     inner join films_genres as fg
                                on f.id = fg.film_id
                     inner join genres as g
                                on fg.genre_id = g.id
                     inner join mpa on f.mpa_id = mpa.id
                     inner join films_likes as fl on f.id = fl.film_id
                     inner join users as u on fl.user_id = u.id
            where fg.film_id=?
            order by film_id, genre_id, user_id;
            """;


    private void filmSetInfo(SqlRowSet rowSet, Film film) {
        int filmId = rowSet.getInt("film_id");
        String filmName = rowSet.getString("film");
        String description = rowSet.getString("description");
        Date releaseDate = rowSet.getDate("release_date");
        int duration = rowSet.getInt("duration");
        int mpaId = rowSet.getInt("mpa_id");
        String mpaName = rowSet.getString("mpa");
        int genreId = rowSet.getInt("genre_id");
        int userId = rowSet.getInt("user_id");

        film.setId(filmId);
        film.setName(filmName);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration);
        film.setMpa(new Mpa(mpaId, mpaName));
        film.getGenres().add(genreId);
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
                if(film.getId() != 0){
                    films.add(film);
                }
                film = new Film();

                String filmName = rowSet.getString("film");
                String description = rowSet.getString("description");
                Date releaseDate = rowSet.getDate("release_date");
                int duration = rowSet.getInt("duration");
                int mpaId = rowSet.getInt("mpa_id");
                String mpaName = rowSet.getString("mpa");
                int genreId = rowSet.getInt("genre_id");
                int userId = rowSet.getInt("user_id");

                film.setId(filmId);
                film.setName(filmName);
                film.setDescription(description);
                film.setReleaseDate(releaseDate);
                film.setDuration(duration);
                film.setMpa(new Mpa(mpaId, mpaName));
                film.getGenres().add(genreId);
                film.getLikes().add(userId);

                filmIdPrev = filmId;
            } else {                                         // добиваем инфу по жанрам и лайкам для уже созданного фильма
                int genreId = rowSet.getInt("genre_id");
                int userId = rowSet.getInt("user_id");
                film.getGenres().add(genreId);
                film.getLikes().add(userId);
            }
        }
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
//                String filmName = rowSet.getString("film");
//                String description = rowSet.getString("description");
//                Date releaseDate = rowSet.getDate("release_date");
//                int duration = rowSet.getInt("duration");
//                int mpaId = rowSet.getInt("mpa_id");
//                String mpaName = rowSet.getString("mpa");
//                int genreId = rowSet.getInt("genre_id");
//                int userId = rowSet.getInt("user_id");
//
//                film.setId(filmId);
//                film.setName(filmName);
//                film.setDescription(description);
//                film.setReleaseDate(releaseDate);
//                film.setDuration(duration);
//                film.setMpa(new Mpa(mpaId, mpaName));
//                film.getGenres().add(genreId);
//                film.getLikes().add(userId);

                filmIdPrev = filmId;
            } else {
                int genreId = rowSet.getInt("genre_id");
                int userId = rowSet.getInt("user_id");
                film.getGenres().add(genreId);
                film.getLikes().add(userId);
            }
        }
        return film;
    }

    @Override
    public Film create(Film user) {
        return null;
    }

    @Override
    public Film update(Film user) {
        return null;
    }
}
