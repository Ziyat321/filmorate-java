package com.practice.filmorate.storage;

import com.practice.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    List<Genre> findAll();

    Genre findById(int id);
}
