package com.practice.filmorate.model;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
public class Mpa {
    int id;

    @NotBlank(message = "Название рейтинга не может быть пустым")
    String name;
}
