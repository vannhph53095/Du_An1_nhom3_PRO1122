package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

import java.util.ArrayList;

public class AddMovieActivity extends AppCompatActivity {
    private RatingBar ratingBaradd;
    private EditText editTextTitle,   editTextDescription, editTextDirector, editTextReleaseYear, editTextFilmSource,editTextUri;
    private Button buttonAddMovie;
    private Spinner spinnerGenre;
    private FirebaseFirestore db;
    private MovieAdapter movieAdapter; // Adapter cho RecyclerView
    private ArrayList<Movie> movieList = new ArrayList<>(); // Danh sách phim
    private ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();
        btnBack = findViewById(R.id.btnbackinmanhinhyeuthich);
        // Ánh xạ view
        editTextUri=findViewById(R.id.editTextUri);
        editTextFilmSource = findViewById(R.id.editTextFilmSource);
        editTextTitle = findViewById(R.id.editTextTitle);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.movie_genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);
        ratingBaradd = findViewById(R.id.ratingBaradd);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextReleaseYear = findViewById(R.id.editTextReleaseYear);
        buttonAddMovie = findViewById(R.id.buttonAddMovie);



        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        buttonAddMovie.setOnClickListener(v -> addMovie());
    }

    private void addMovie() {

        String title = editTextTitle.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();
        float rating = ratingBaradd.getRating();

        String description = editTextDescription.getText().toString().trim();
        String director = editTextDirector.getText().toString().trim();
        String releaseYear = editTextReleaseYear.getText().toString().trim();
        String filmSource = editTextFilmSource.getText().toString().trim();


        if (title.isEmpty() || genre.isEmpty()  || description.isEmpty() || director.isEmpty() || releaseYear.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }


        float ratingValue;
        int releaseYearValue;
        try {

            releaseYearValue = Integer.parseInt(releaseYear);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập giá trị hợp lệ cho rating và năm phát hành!", Toast.LENGTH_SHORT).show();
            return;
        }
int watched=0;
        // Tạo ID và đối tượng Movie
        String id = db.collection("movies").document().getId();
        String posterUri = editTextUri.getText().toString().trim(); // Giả sử bạn có một EditText cho posterUri

        Movie movie = new Movie(title, genre, rating, description, director, releaseYearValue, posterUri, filmSource ,watched);
        movie.setId(id);

        db.collection("movies").document(id)
                .set(movie)
                .addOnSuccessListener(aVoid -> {
                    movieList.add(movie);
                    movieAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(AddMovieActivity.this, TrangChu.class);
                    intent.putExtra("movie_added", title); // Truyền tên phim

sendNotification(title);
                    Toast.makeText(this, "Thêm phim thành công!", Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    private void sendNotification(String title) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "movie_channel",
                    "Movie Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }


        RemoteViews customView = new RemoteViews(getPackageName(), R.layout.item_notifi);
        customView.setTextViewText(R.id.titlenotifi, title + " đã được thêm vào");

        Intent intent = new Intent(this, ManHinhDangNhap.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "movie_channel")
                .setSmallIcon(R.drawable.ic_movie)
                .setCustomContentView(customView)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        notificationManager.notify(1, builder.build());
    }



    private void clearFields() {
        editTextTitle.setText("");

        ratingBaradd.setRating(0);
        editTextDescription.setText("");
        editTextDirector.setText("");
        editTextReleaseYear.setText("");
        editTextFilmSource.setText("");
    }
}
