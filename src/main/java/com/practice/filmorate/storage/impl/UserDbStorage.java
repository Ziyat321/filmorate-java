package com.practice.filmorate.storage.impl;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    private final static String SELECT = """
            select *
            from users
            """;

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SELECT, this::mapRow);
    }

    @Override
    public User findById(int id) {
        String sql = SELECT + " where id=?";
        return jdbcTemplate.query(sql, this::mapRow, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователя с данным " + id + " не существует"));
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> params = Map.of(
                "email", user.getEmail(),
                "login", user.getLogin(),
                "name", user.getName(),
                "birthday", user.getBirthday()
        );
        int id = insert.executeAndReturnKey(params).intValue();
        user.setId(id);
        return user;
    }

    @Override
    public User update(User user) {
        String sql = """
                update users
                set email = ?,
                    login = ?,
                    name = ?,
                    birthday = ?
                where id = ?
                """;

        int result = jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());

        if(result == 0) throw new NotFoundException("...");
        return user;
    }

    @Override
    public void addFriend(int userId, int friendId) {

    }

    @Override
    public void removeFriend(int userId, int friendId) {

    }

    @Override
    public List<User> findAllFriends(int userId) {
        return List.of();
    }

    @Override
    public List<User> findAllCommonFriends(int userId, int friendId) {
        return List.of();
    }

    private User mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String login = rs.getString("login");
        String email = rs.getString("email");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return new User(id, email, login, name, birthday);
    }
}
