package fpoly.ph53095.nhom3_du_an_1_pro1122;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

        // Gọi phương thức để tải các phim đã xem từ SharedPreferences
        loadWatchedMovies();
    }

    private void loadWatchedMovies() {
        SharedPreferences sharedPreferences = getSharedPreferences("movie_preferences", Context.MODE_PRIVATE);

        // Lấy tất cả các phim đã xem từ SharedPreferences
        for (String movieId : sharedPreferences.getAll().keySet()) {
            boolean isWatched = sharedPreferences.getBoolean(movieId, false);
            if (isWatched) {
                // Tạo đối tượng Movie từ Firestore hoặc từ danh sách đã có
                // Ví dụ: Movie movie = getMovieById(movieId); (Hàm này lấy phim từ Firestore nếu cần)
                Movie movie = getMovieById(movieId);  // Ví dụ, bạn có thể tạo một hàm lấy thông tin phim từ Firestore
                watchedMovies.add(movie);
            }
        }

        // Cập nhật adapter với danh sách phim đã xem
        movieAdapter = new MovieAdapter(this, watchedMovies, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                // Xử lý click vào phim khi xem lịch sử
            }

            @Override
            public void onMovieLongClick(Movie movie) {
                // Xử lý long click vào phim nếu cần
            }
        });

        recyclerView.setAdapter(movieAdapter);
    }

    // Phương thức này giả sử bạn có một cách để lấy Movie từ Firestore nếu cần
    private Movie getMovieById(String movieId) {
        // Truy vấn Firestore để lấy thông tin chi tiết phim, ví dụ:
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Tạo đối tượng Movie và lấy thông tin từ Firestore (có thể dùng callback hoặc Future)
        return new Movie();  // Trả về một Movie giả lập, bạn cần điều chỉnh theo logic thực tế
    }
}
