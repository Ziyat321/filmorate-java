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
where fg.film_id=1
order by film_id, genre_id, user_id;



