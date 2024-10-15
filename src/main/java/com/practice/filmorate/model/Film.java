package com.practice.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Film {
    int id;

    @NotBlank(message = "Название фильма не может быть пустым")
    String name;

    @Size(max = 200, message = "Максимальная длинна сообщения 200 символов")
    String description;

    LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительной")
    int duration;

    @NotNull(message = "Рейтинг должен присутствовать")
    Mpa mpa;

    final Set<Genre> genres = new HashSet<>();

    final Set<Integer> likes = new HashSet<>();
}