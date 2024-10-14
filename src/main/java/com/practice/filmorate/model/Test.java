package com.practice.filmorate.model;

public class Test {
    public static void main(String[] args) {
        Film film1 = new Film();
        System.out.println(film1.getId());
        System.out.println(film1.getName());


        Film film2 = new Film();
        film2.setId(1);
        film2.setName("film2");
        System.out.println(film2.getId());
        System.out.println(film2.getName());
        System.out.println(film2.getName().equals(film1.getName()));


    }
}
