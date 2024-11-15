package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

public class EditMovieActivity extends AppCompatActivity {
    private EditText editTextTitle, editTextGenre, editTextRating, editTextDescription, editTextDirector, editTextReleaseYear;
    private Button buttonEditMovie;
    private FirebaseFirestore db;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        movie = (Movie) getIntent().getSerializableExtra("MOVIE");

        db = FirebaseFirestore.getInstance();

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextGenre = findViewById(R.id.editTextGenre);
        editTextRating = findViewById(R.id.editTextRating);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextReleaseYear = findViewById(R.id.editTextReleaseYear);
        buttonEditMovie = findViewById(R.id.buttonEditMovie);

        editTextTitle.setText(movie.getTitle());
        editTextGenre.setText(movie.getGenre());
        editTextRating.setText(String.valueOf(movie.getRating()));
        editTextDescription.setText(movie.getDescription());
        editTextDirector.setText(movie.getDirector());
        editTextReleaseYear.setText(String.valueOf(movie.getReleaseYear()));

        buttonEditMovie.setOnClickListener(v -> updateMovie());
    }

    private void updateMovie() {
        String title = editTextTitle.getText().toString().trim();
        String genre = editTextGenre.getText().toString().trim();
        String rating = editTextRating.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String director = editTextDirector.getText().toString().trim();
        String releaseYear = editTextReleaseYear.getText().toString().trim();

        float ratingValue = Float.parseFloat(rating);
        String releaseYearValue = String.valueOf(Integer.parseInt(releaseYear));

        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setRating(ratingValue);
        movie.setDescription(description);
        movie.setDirector(director);
        movie.setReleaseYear(Integer.parseInt(releaseYearValue));

        db.collection("movies").document(movie.getId())
                .set(movie)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditMovieActivity.this, "Thông tin phim đã được cập nhật", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditMovieActivity.this, "Lỗi khi cập nhật phim: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
