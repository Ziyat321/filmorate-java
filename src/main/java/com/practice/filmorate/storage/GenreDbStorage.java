package com.practice.filmorate.storage;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    private final static String SELECT = """
            select *
            from genres
            """;

    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query(SELECT, this::mapRow);
    }

    @Override
    public Genre findById(int id) {
        String sql = SELECT + " where id=?";
        return jdbcTemplate.queryForStream(sql, this::mapRow, id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Жанра с данным " + id + " не существует"));
    }

    private Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Genre(id, name);
    }
}
