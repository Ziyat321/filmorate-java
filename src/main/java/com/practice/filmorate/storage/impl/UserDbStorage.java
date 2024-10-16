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

        if(result == 0) throw new NotFoundException("Пользователя с данным " + user.getId() + " не существует");
        return user;
    }

    @Override
    public void addFriend(int userId, int friendId) {
        findById(userId);
        findById(friendId);
        String sqlSelect = """
                select *
                from friends
                where friend1 = ?
                  and friend2 = ?;
                """;
        String sqlInsert = """
                    insert into friends (friend1, friend2)
                    values (?, ?);
                    """;
        if(!jdbcTemplate.queryForRowSet(sqlSelect, userId, friendId).next()){
            jdbcTemplate.update(sqlInsert, userId, friendId);
        }
        if(!jdbcTemplate.queryForRowSet(sqlSelect, friendId, userId).next()){
            jdbcTemplate.update(sqlInsert, friendId, userId);
        }
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        findById(userId);
        findById(friendId);
        String sqlDelete = """
                delete from friends
                where friend1 = ?
                and friend2 = ?;
                """;
        jdbcTemplate.update(sqlDelete, userId, friendId);
        jdbcTemplate.update(sqlDelete, friendId, userId);
    }

    @Override
    public List<User> findAllFriends(int userId) {
        findById(userId);
        String sql = """
                select friend2 as id,
                       u.email as email,
                       u.login as login,
                       u.name as name,
                       u.birthday as birthday
                from friends as f
                         inner join users as u
                                    on u.id = f.friend2
                where friend1 = ?
                order by id;
                """;
        return jdbcTemplate.query(sql, this::mapRow, userId);
    }

    @Override
    public List<User> findAllCommonFriends(int userId, int otherUserId) {
        findById(userId);
        findById(otherUserId);
        String sql = """
                select friend2    as id,
                       u.email    as email,
                       u.login    as login,
                       u.name     as name,
                       u.birthday as birthday
                from friends as f
                         inner join users as u
                                    on u.id = f.friend2
                where friend1 = ?
                intersect
                select friend2    as id,
                       u.email    as email,
                       u.login    as login,
                       u.name     as name,
                       u.birthday as birthday
                from friends as f
                         inner join users as u
                                    on u.id = f.friend2
                where friend1 = ?
                order by id;
                """;
        return jdbcTemplate.query(sql, this::mapRow, userId, otherUserId);
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
