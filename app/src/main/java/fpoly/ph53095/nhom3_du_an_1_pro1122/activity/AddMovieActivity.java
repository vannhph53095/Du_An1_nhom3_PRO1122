package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

import java.util.ArrayList;

public class AddMovieActivity extends AppCompatActivity {

    private EditText editTextTitle,  editTextRating, editTextDescription, editTextDirector, editTextReleaseYear, editTextFilmSource,editTextUri;
    private Button buttonAddMovie;
    private Spinner spinnerGenre;
    private FirebaseFirestore db;
    private MovieAdapter movieAdapter; // Adapter cho RecyclerView
    private ArrayList<Movie> movieList = new ArrayList<>(); // Danh sách phim

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        // Ánh xạ view
        editTextUri=findViewById(R.id.editTextUri);
        editTextFilmSource = findViewById(R.id.editTextFilmSource);
        editTextTitle = findViewById(R.id.editTextTitle);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.movie_genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);
        editTextRating = findViewById(R.id.editTextRating);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextReleaseYear = findViewById(R.id.editTextReleaseYear);
        buttonAddMovie = findViewById(R.id.buttonAddMovie);

        // Khởi tạo adapter
        movieAdapter = new MovieAdapter(movieList, this);

        // Xử lý sự kiện khi nhấn nút thêm phim
        buttonAddMovie.setOnClickListener(v -> addMovie());
    }

    private void addMovie() {
        // Lấy dữ liệu từ các ô nhập liệu
        String title = editTextTitle.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();
        String rating = editTextRating.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String director = editTextDirector.getText().toString().trim();
        String releaseYear = editTextReleaseYear.getText().toString().trim();
        String filmSource = editTextFilmSource.getText().toString().trim();

        // Kiểm tra dữ liệu nhập liệu
        if (title.isEmpty() || genre.isEmpty() || rating.isEmpty() || description.isEmpty() || director.isEmpty() || releaseYear.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chuyển đổi giá trị rating và năm phát hành
        float ratingValue;
        int releaseYearValue;
        try {
            ratingValue = Float.parseFloat(rating);
            releaseYearValue = Integer.parseInt(releaseYear);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập giá trị hợp lệ cho rating và năm phát hành!", Toast.LENGTH_SHORT).show();
            return;
        }
int watched=0;
        // Tạo ID và đối tượng Movie
        String id = db.collection("movies").document().getId();
        String posterUri = editTextUri.getText().toString().trim(); // Giả sử bạn có một EditText cho posterUri

        Movie movie = new Movie(title, genre, ratingValue, description, director, releaseYearValue, posterUri, filmSource ,watched);
        movie.setId(id);

        db.collection("movies").document(id)
                .set(movie)
                .addOnSuccessListener(aVoid -> {
                    movieList.add(movie);
                    movieAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Thêm phim thành công!", Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void clearFields() {
        editTextTitle.setText("");

        editTextRating.setText("");
        editTextDescription.setText("");
        editTextDirector.setText("");
        editTextReleaseYear.setText("");
        editTextFilmSource.setText("");
    }
}
