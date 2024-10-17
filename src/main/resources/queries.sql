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
where fg.film_id = 1
order by film_id, genre_id, user_id;


select fl.film_id as film_id,
       fl.user_id as user_id
from films_likes as fl
where film_id = 1
  and user_id = 3;

insert into films_likes(film_id, user_id)
values (1, 5);

delete
from films_likes
where film_id = 1
  and user_id = 5;

select friend2    as id,
       u.email    as email,
       u.login    as login,
       u.name     as name,
       u.birthday as birthday
from friends as f
         inner join users as u
                    on u.id = f.friend2
where friend1 = 1
order by id;


select *
from friends
where friend1 = 1
  and friend2 = 5;


select friend2    as id,
       u.email    as email,
       u.login    as login,
       u.name     as name,
       u.birthday as birthday
from friends as f
         inner join users as u
                    on u.id = f.friend2
where friend1 = 2
intersect
select friend2    as id,
       u.email    as email,
       u.login    as login,
       u.name     as name,
       u.birthday as birthday
from friends as f
         inner join users as u
                    on u.id = f.friend2
where friend1 = 3
order by id;


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
where f.id=13
order by film_id, genre_id, user_id;

