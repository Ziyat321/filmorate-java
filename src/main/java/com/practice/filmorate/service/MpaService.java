package com.practice.filmorate.service;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaStorage mpaStorage;

    public List<Mpa> findAll(){
        return mpaStorage.findAll();
    }

    public Mpa findById(int id){
        // TODO replace message
        return mpaStorage.findById(id).orElseThrow(() -> new NotFoundException("..."));
    }
}
