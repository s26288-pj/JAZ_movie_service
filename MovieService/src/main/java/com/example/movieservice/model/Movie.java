package com.example.movieservice.model;

public class Movie {
    private int ID;
    private String name;
    private String category;
    private String director;

    public Movie(int ID, String name, String category, String director) {
        this.ID = ID;
        this.name = name;
        this.category = category;
        this.director = director;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
