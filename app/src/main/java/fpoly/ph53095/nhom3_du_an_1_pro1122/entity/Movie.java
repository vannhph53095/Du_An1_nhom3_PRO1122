package fpoly.ph53095.nhom3_du_an_1_pro1122.entity;
import com.google.firebase.firestore.PropertyName;

public class Movie {
    private String title;
    private String genre;
    private float rating;
    private int posterResId;
    private String description;
    private String director;
    private int releaseYear;


    public Movie() {
    }


    public Movie(String title, String genre, float rating, int posterResId, String description, String director, int releaseYear) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.posterResId = posterResId;
        this.description = description;
        this.director = director;
        this.releaseYear = releaseYear;
    }

    @PropertyName("title")
    public String getTitle() {
        return title;
    }

    @PropertyName("genre")
    public String getGenre() {
        return genre;
    }

    @PropertyName("rating")
    public float getRating() {
        return rating;
    }

    @PropertyName("posterResId")
    public int getPosterResId() {
        return posterResId;
    }

    @PropertyName("description")
    public String getDescription() {
        return description;
    }

    @PropertyName("director")
    public String getDirector() {
        return director;
    }

    @PropertyName("releaseYear")
    public int getReleaseYear() {
        return releaseYear;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setPosterResId(int posterResId) {
        this.posterResId = posterResId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}