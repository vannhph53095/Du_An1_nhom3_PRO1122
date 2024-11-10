package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Movie;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class AddMovieActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextGenre, editTextRating, editTextDescription, editTextDirector, editTextReleaseYear;
    private Button buttonAddMovie;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        // Ánh xạ các view
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextGenre = findViewById(R.id.editTextGenre);
        editTextRating = findViewById(R.id.editTextRating);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextReleaseYear = findViewById(R.id.editTextReleaseYear);
        buttonAddMovie = findViewById(R.id.buttonAddMovie);

        // Thiết lập sự kiện nhấn nút
        buttonAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovie();
            }
        });
    }

    private void addMovie() {
        String title = editTextTitle.getText().toString().trim();
        String genre = editTextGenre.getText().toString().trim();
        String rating = editTextRating.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String director = editTextDirector.getText().toString().trim();
        String releaseYear = editTextReleaseYear.getText().toString().trim();

        // Kiểm tra xem các trường có rỗng không
        if (title.isEmpty() || genre.isEmpty() || rating.isEmpty() || description.isEmpty() || director.isEmpty() || releaseYear.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chuyển đổi giá trị rating và releaseYear
        float ratingValue = Float.parseFloat(rating);
        int releaseYearValue = Integer.parseInt(releaseYear);

        // Giả sử bạn có một ID tài nguyên cho poster (hoặc URL)
        int posterResId = R.drawable.iteam4; // Thay đổi thành ID tài nguyên thực tế hoặc URL

        // Tạo một đối tượng phim
        Movie movie = new Movie(title, genre, ratingValue, posterResId, description, director, releaseYearValue);

        // Thêm phim vào Firestore
        db.collection("movies")
                .add(movie)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddMovieActivity.this, "Phim đã được thêm thành công!", Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddMovieActivity.this, "Lỗi khi thêm phim: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void clearFields() {
        editTextTitle.setText("");
        editTextGenre.setText("");
        editTextRating.setText("");
        editTextDescription.setText("");
        editTextDirector.setText("");
        editTextReleaseYear.setText("");
    }
}