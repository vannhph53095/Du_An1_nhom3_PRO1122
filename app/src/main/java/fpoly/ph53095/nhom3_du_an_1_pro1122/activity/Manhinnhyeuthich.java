package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

public class Manhinnhyeuthich extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter favoriteAdapter;
    private ArrayList<Movie> favoriteMovies;
    private ImageView accout_ic, mhyeuthichbutton, home_icon;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manhinnhyeuthich);

        email = getIntent().getStringExtra("email");
        home_icon = findViewById(R.id.home_icon);
        home_icon.setOnClickListener(v -> {
            Intent intent = new Intent(Manhinnhyeuthich.this, TrangChu.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });
        mhyeuthichbutton = findViewById(R.id.mhyeuthichbutton);
        mhyeuthichbutton.setOnClickListener(v -> {
            Intent intent = new Intent(Manhinnhyeuthich.this, Manhinnhyeuthich.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        });

        accout_ic = findViewById(R.id.accout_ic);
        accout_ic.setOnClickListener(v -> {
            if ("vannhph53095@gmail.com".equals(email)) {
                Intent intent = new Intent(Manhinnhyeuthich.this, Manhinhadmin.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(Manhinnhyeuthich.this, Manhinhclient.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.listyeuthich);
        favoriteMovies = new ArrayList<>();
        favoriteAdapter = new MovieAdapter(this, favoriteMovies, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {

                Intent intent = new Intent(Manhinnhyeuthich.this, manhinhxemphim.class);
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("genre", movie.getGenre());
                intent.putExtra("description", movie.getDescription());
                intent.putExtra("director", movie.getDirector());
                intent.putExtra("releaseYear", movie.getReleaseYear());
                intent.putExtra("rating", movie.getRating());
                intent.putExtra("filmSource", movie.getFilmSource());
                intent.putExtra("posterUri", movie.getPosterUri());
                startActivity(intent);
                finish();
            }

            @Override
            public void onMovieLongClick(Movie movie) {
                // Xử lý long click vào phim
                Toast.makeText(Manhinnhyeuthich.this, "Long click vào phim: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(favoriteAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Tải danh sách yêu thích
        loadFavoriteMovies();
    }

    private void loadFavoriteMovies() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("movies")
                .whereEqualTo("isLiked", true)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        favoriteMovies.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            favoriteMovies.add(movie);
                        }
                        favoriteAdapter.notifyDataSetChanged(); // Cập nhật adapter với danh sách mới
                    } else {
                        Toast.makeText(Manhinnhyeuthich.this, "Lỗi khi tải danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
