package com.practice.filmorate.storage.impl;

import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
    public Optional<Mpa> findById(int id) {
        String sql = SELECT + " where id = ?";
        return jdbcTemplate.query(sql, this::mapRow, id)
                .stream()
                .findFirst();
    }

    private Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Mpa(id, name);
    }
}
