package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

public class TrangChu extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;
    private String email;
    private ImageView accout_ic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        // Lấy email từ intent
        email = getIntent().getStringExtra("email");

        recyclerView = findViewById(R.id.recyclerView);
        db = FirebaseFirestore.getInstance();
        movieList = new ArrayList<>();

        movieAdapter = new MovieAdapter(this, movieList, this); // Truyền `this` cho callback
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        loadMoviesFromFirestore();

        accout_ic = findViewById(R.id.accout_ic);
        accout_ic.setOnClickListener(v -> {
            if ("vannhph53095@gmail.com".equals(email)) {
                Intent intent = new Intent(TrangChu.this, Manhinhadmin.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(TrangChu.this, Manhinhclient.class);
                startActivity(intent);
            }
        });
    }

    private void loadMoviesFromFirestore() {
        db.collection("movies").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        movieList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            movieList.add(movie);
                        }
                        movieAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Lỗi khi tải phim: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onMovieClick(Movie movie) {
        // Khi click vào item, mở màn hình chi tiết phim
        Intent intent = new Intent(TrangChu.this, manhinhxemphim.class);
        intent.putExtra("filmSource", movie.getFilmSource()); // Truyền tên video
        startActivity(intent);



    }

    @Override
    public void onMovieLongClick(Movie movie) {
        // Không xử lý gì ở Trang Chủ vì chỉ cần click vào item
    }
}