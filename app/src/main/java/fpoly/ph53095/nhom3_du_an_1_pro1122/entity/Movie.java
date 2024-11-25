package fpoly.ph53095.nhom3_du_an_1_pro1122.entity;

import java.io.Serializable;

public class Movie implements Serializable {
    private String id; // ID của phim (có thể là Firebase document ID)
    private String title; // Tiêu đề phim
    private String genre; // Thể loại phim
    private float rating; // Đánh giá phim
    private String description; // Mô tả phim
    private String director; // Đạo diễn
    private int releaseYear; // Năm phát hành
    private String posterUri; // URL của poster phim
    private String filmSource; // Nguồn phim (file video)
    private boolean isLiked; // Trạng thái "Yêu thích"
    private int Watched;
    // Constructor không đối số
    public Movie() {
    }

    // Constructor đầy đủ thông tin
    public Movie(String title, String genre, float rating, String description, String director, int releaseYear, String posterUri, String filmSource, int watched) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.director = director;
        this.releaseYear = releaseYear;
        this.posterUri = posterUri;
        this.filmSource = filmSource;
        this.Watched = watched;
    }






    public int getWatched() {
        return Watched;
    }

    public void setWatched(int watched) {
        this.Watched = watched;
    }


    // Getter và Setter cho ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter và Setter cho Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter và Setter cho Genre
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Getter và Setter cho Rating
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    // Getter và Setter cho Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter và Setter cho Director
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    // Getter và Setter cho Release Year
    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    // Getter và Setter cho Poster Uri
    public String getPosterUri() {
        return posterUri;
    }

    public void setPosterUri(String posterUri) {
        this.posterUri = posterUri;
    }

    // Getter và Setter cho Film Source
    public String getFilmSource() {return filmSource;
    }

    public void setFilmSource(String filmSource) {
        this.filmSource = filmSource;
    }

    // Getter và Setter cho trạng thái "Yêu thích"
    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }
}