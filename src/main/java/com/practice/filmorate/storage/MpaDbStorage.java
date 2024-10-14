package com.practice.filmorate.storage;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.Mpa;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    private final static String SELECT = "select * from mpa";

    @Override
    public List<Mpa> findAll() {
        return jdbcTemplate.query(SELECT, this::mapRow);
    }

    @Override
    public Mpa findById(int id) {
        String sql = SELECT + " where id = ?";
        return jdbcTemplate.queryForStream(sql, this::mapRow, id)
                .findFirst()
                .orElseThrow(()->new NotFoundException("Рейтинга с данным " + id + " не существует"));
    }

    private Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Mpa(id, name);
    }
}
