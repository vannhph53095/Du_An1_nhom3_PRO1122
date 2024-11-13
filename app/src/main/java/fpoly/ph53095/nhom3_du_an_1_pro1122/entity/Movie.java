package fpoly.ph53095.nhom3_du_an_1_pro1122.entity;

import java.io.Serializable;

public class Movie implements Serializable {
    private String id;
    private String title;
    private String genre;
    private float rating;
    private String description;
    private String director;
    private int releaseYear;
    private String posterUri;

    // Constructor không đối số
    public Movie() { }

    // Constructor có đối số
    public Movie(String title, String genre, float rating, String description, String director, int releaseYear, String posterUri) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.director = director;
        this.releaseYear = releaseYear;
        this.posterUri = posterUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosterUri() {
        return posterUri;
    }

    public void setPosterUri(String posterUri) {
        this.posterUri = posterUri;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getters và Setters
    //...
}
