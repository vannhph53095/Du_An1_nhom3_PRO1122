package fpoly.ph53095.nhom3_du_an_1_pro1122;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.activity.Manhinnhyeuthich;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;
public class Manhinhlichsu extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> watchedMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manhinhlichsu);

        // Lắng nghe window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.listlichsu);
        watchedMovies = new ArrayList<>();

        // Gọi phương thức để tải các phim đã xem
        loadWatchedMovies();
    }

    private void loadWatchedMovies() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("movies")
                .whereEqualTo("isWatched", true)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        watchedMovies.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            watchedMovies.add(movie);
                        }
                        // Cập nhật adapter với danh sách mới
                        movieAdapter = new MovieAdapter(this, watchedMovies, new MovieAdapter.OnMovieClickListener() {
                            @Override
                            public void onMovieClick(Movie movie) {
                                // Xử lý click nếu cần
                            }

                            @Override
                            public void onMovieLongClick(Movie movie) {
                                // Xử lý long click nếu cần
                            }
                        });
                        recyclerView.setAdapter(movieAdapter);
                    } else {
                        Toast.makeText(Manhinhlichsu.this, "Lỗi khi tải danh sách phim đã xem", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
