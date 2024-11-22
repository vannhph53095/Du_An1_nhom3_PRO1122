package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.viewPagerAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

public class TrangChu extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;

    private String email;
    private ImageView accout_ic,mhyeuthichbutton,home_icon;
    private ViewPager viewPagerMain;
    private viewPagerAdapter pagerAdapter;

    private int currentPage = 0;
    private final int SLIDE_DELAY = 3000;
    private Handler sliderHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);


        viewPagerMain = findViewById(R.id.viewPagerMain);

        int[] images = {
                R.drawable.iteam1,
                R.drawable.iteam2,
                R.drawable.iteam3
        };

        pagerAdapter = new viewPagerAdapter(this, images);
        viewPagerMain.setAdapter(pagerAdapter);

        // Khởi chạy slide tự động
        sliderHandler.postDelayed(slideRunnable, SLIDE_DELAY);

        // Nhận email từ Intent


        // Khởi tạo RecyclerView và Firebase
        recyclerView = findViewById(R.id.recyclerView);
        db = FirebaseFirestore.getInstance();
        movieList = new ArrayList<>();

        // Cài đặt adapter cho RecyclerView
        movieAdapter = new MovieAdapter(this, movieList, this);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Tải danh sách phim từ Firestore
        loadMoviesFromFirestore();

        // Sự kiện click icon tài khoản
        email = getIntent().getStringExtra("email");
home_icon=findViewById(R.id.home_icon);
        home_icon.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChu.this, TrangChu.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });
        mhyeuthichbutton=findViewById(R.id.mhyeuthichbutton);
        mhyeuthichbutton.setOnClickListener(v -> {

            Intent intent = new Intent(TrangChu.this, Manhinnhyeuthich.class);
            intent.putExtra("email", email);
            startActivity(intent);

        });

        accout_ic = findViewById(R.id.accout_ic);
        accout_ic.setOnClickListener(v -> {
            if ("vannhph53095@gmail.com".equals(email)) {
                Intent intent = new Intent(TrangChu.this, Manhinhadmin.class);
                intent.putExtra("email", email);
                startActivity(intent);

            } else {
                Intent intent = new Intent(TrangChu.this, Manhinhclient.class);
                startActivity(intent);

            }
        });
    }

    // Runnable để thay đổi slide tự động
    private final Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            if (pagerAdapter.getCount() > 0) {
                currentPage = (currentPage + 1) % pagerAdapter.getCount();
                viewPagerMain.setCurrentItem(currentPage, true);
                sliderHandler.postDelayed(this, SLIDE_DELAY);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (Movie movie : movieList) {
            db.collection("movies").document(movie.getId())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            boolean isLiked = documentSnapshot.getBoolean("isLiked");
                            movie.setLiked(isLiked);
                            movieAdapter.notifyItemChanged(movieList.indexOf(movie));
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi tải dữ liệu từ Firestore", Toast.LENGTH_SHORT).show();
                    });
        }
        sliderHandler.postDelayed(slideRunnable, SLIDE_DELAY);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(slideRunnable);
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
        Intent intent = new Intent(TrangChu.this, manhinhxemphim.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("genre", movie.getGenre());
        intent.putExtra("description", movie.getDescription());
        intent.putExtra("director", movie.getDirector());
        intent.putExtra("releaseYear", movie.getReleaseYear());
        intent.putExtra("rating", movie.getRating());
        intent.putExtra("filmSource", movie.getFilmSource());
        intent.putExtra("posterUri", movie.getPosterUri());
        startActivity(intent);

    }

    @Override
    public void onMovieLongClick(Movie movie) {

    }
}
