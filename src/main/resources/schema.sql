create table if not exists films
(
    id           serial primary key,
    name         varchar      not null,
    description  varchar(200) not null,
    release_date date         not null,
    duration     int
        constraint positive_check check ( duration > 0),
    mpa_id int not null,
    foreign key (mpa_id) references mpa(id)
);

create table if not exists genres
(
    id   serial primary key,
    name varchar not null
);

create table if not exists mpa(
                                  id serial,
                                  name varchar not null
);

create table if not exists films_genres(
                                           id serial primary key,
                                           film_id int not null,
                                           genre_id int not null,
                                           foreign key (film_id) references films(id),
                                           foreign key (genre_id) references genres(id)
);

create table if not exists users
(
    id    serial primary key,
    email varchar not null
        constraint email_check check ( email like '%.@%'),
    login  varchar not null,
    birthday date not null
);

create table if not exists films_likes(
                                          id serial primary key,
                                          film_id int not null,
                                          user_id int not null,
                                          foreign key (film_id) references films(id),
                                          foreign key (user_id) references users(id)
);

create table if not exists friends(
                                      id serial primary key,
                                      friend1 int not null,
                                      friend2 int not null,
                                      foreign key (friend1) references users(id),
                                      foreign key (friend2) references users(id),
                                      unique (friend1, friend2)
);