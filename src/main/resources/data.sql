insert into mpa(name)
values ('G'),
       ('PG'),
       ('PG-13'),
       ('R'),
       ('NC-17');

insert into genres(name)
values ('Комедия'),
       ('Драма'),
       ('Мультфильм'),
       ('Триллер'),
       ('Документальный'),
       ('Боевик');

-- insert into films(name, description, release_date, duration, mpa_id)
-- values ('film1', 'description1', '2001-02-02', 90, 1),
--        ('film2', 'description2', '2002-02-02', 100, 1),
--        ('film3', 'description3', '2003-02-02', 100, 2),
--        ('film4', 'description4', '2004-02-02', 100, 3),
--        ('film5', 'description5', '2005-02-02', 100, 3);
--
-- insert into films_genres(film_id, genre_id)
-- values (1, 1),
--        (1, 3),
--        (2, 1),
--        (3, 4),
--        (3, 5),
--        (3, 6),
--        (4, 2),
--        (5, 6);
--
-- insert into users(email, login, name, birthday)
-- values ('email1@.com', 'login1', 'name1', '2000-01-01'),
--        ('email2@.com', 'login2', 'name2', '2001-01-01'),
--        ('email3@.com', 'login3', 'name3', '2002-01-01'),
--        ('email4@.com', 'login4', 'name4', '2003-01-01'),
--        ('email5@.com', 'login5', 'name5', '2004-01-01');
--
-- insert into films_likes(film_id, user_id)
-- values (1, 1),
--        (1, 3),
--        (1, 4),
--        (2, 2),
--        (2, 5),
--        (3, 1),
--        (3, 5),
--        (4, 5),
--        (5, 2);
--
-- insert into friends(friend1, friend2)
-- values (1, 2),
--        (1, 3),
--        (2, 4),
--        (2, 5),
--        (3, 5),
--        (4, 5);