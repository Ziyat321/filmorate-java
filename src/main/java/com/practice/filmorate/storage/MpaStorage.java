package com.practice.filmorate.storage;

import com.practice.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {
    List<Mpa> findAll();

    Mpa findById(int id);
}
