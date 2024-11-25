package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdaptertop10;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.viewPagerAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Manhinhtheloai;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

public class TrangChu extends AppCompatActivity implements MovieAdapter.OnMovieClickListener, MovieAdaptertop10.OnMovieClickListener {

    // UI Components
    private RecyclerView recyclerView, recyclerViewTop10;
    private ViewPager viewPagerMain;
    private ImageView accountIcon, favoritesButton, homeIcon, categoryButton;
    private SearchView searchView;

    // Adapters and Data
    private MovieAdapter movieAdapter;
    private MovieAdaptertop10 movieAdapterTop10;
    private List<Movie> movieList, movieListTop10;
    // Firebase
    private FirebaseFirestore db;
    // ViewPager Settings
    private viewPagerAdapter pagerAdapter;
    private int currentPage = 0;
    private final int SLIDE_DELAY = 3000;
    private final Handler sliderHandler = new Handler();


    // User Information
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovies(query); // Gọi hàm tìm kiếm
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMovies(newText); // Gợi ý tìm kiếm khi nhập
                return true;
            }
        });

        // Initialize UI components and data
        initUI();
        setupViewPager();
        setupRecyclerViews();

        // Load data
        loadMoviesFromFirestore();
        loadTop10MoviesFromFirestore();

        // Set up event listeners
        setupEventListeners();
    }

    private void initUI() {
        // Initialize UI elements
        viewPagerMain = findViewById(R.id.viewPagerMain);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewTop10 = findViewById(R.id.recyclerViewtop10);
        categoryButton = findViewById(R.id.manhinhtheloaibtn);
        homeIcon = findViewById(R.id.home_icon);
        favoritesButton = findViewById(R.id.mhyeuthichbutton);
        accountIcon = findViewById(R.id.accout_ic);
        searchView = findViewById(R.id.searchView);

        // Firebase initialization
        db = FirebaseFirestore.getInstance();
        movieList = new ArrayList<>();
        movieListTop10 = new ArrayList<>();
    }

    private void setupViewPager() {
        int[] images = {R.drawable.iteam1, R.drawable.iteam2, R.drawable.iteam3};
        pagerAdapter = new viewPagerAdapter(this, images);
        viewPagerMain.setAdapter(pagerAdapter);

        // Auto-slide functionality
        sliderHandler.postDelayed(slideRunnable, SLIDE_DELAY);
    }

    private void setupRecyclerViews() {
        // Main movie list
        movieAdapter = new MovieAdapter(this, movieList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(movieAdapter);

        // Top 10 movies
        movieAdapterTop10 = new MovieAdaptertop10(this, movieListTop10, this);
        recyclerViewTop10.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTop10.setAdapter(movieAdapterTop10);
    }

    private void setupEventListeners() {
        categoryButton.setOnClickListener(v -> startActivity(new Intent(TrangChu.this, Manhinhtheloai.class)));

        email = getIntent().getStringExtra("email");

        homeIcon.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChu.this, TrangChu.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });

        favoritesButton.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChu.this, Manhinnhyeuthich.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });

        accountIcon.setOnClickListener(v -> {
            Intent intent = ("vannhph53095@gmail.com".equals(email) || "anhtvph52826@gmail.com".equals(email) || "tunaph52894@gmail.com".equals(email))
                    ? new Intent(TrangChu.this, Manhinhadmin.class)
                    : new Intent(TrangChu.this, Manhinhclient.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });
    }

    private void loadMoviesFromFirestore() {
        db.collection("movies").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                movieList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Movie movie = document.toObject(Movie.class);
                    movieList.add(movie);
                }
                movieAdapter.notifyDataSetChanged();
            } else {
                showToast("Lỗi khi tải phim: " + task.getException().getMessage());
            }
        });
    }

    private void loadTop10MoviesFromFirestore() {
        db.collection("movies")
                .orderBy("watched", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        movieListTop10.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            movieListTop10.add(movie);
                        }
                        movieAdapterTop10.notifyDataSetChanged();
                    } else {
                        showToast("Lỗi khi tải phim: " + task.getException().getMessage());
                    }
                });
    }

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
        sliderHandler.postDelayed(slideRunnable, SLIDE_DELAY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(slideRunnable);
    }

    @Override
    public void onMovieClick(Movie movie) {
        db.collection("movies").document(movie.getId())
                .update("watched", FieldValue.increment(1))
                .addOnSuccessListener(aVoid -> {
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
                });
    }

    @Override
    public void onMovieLongClick(Movie movie) {
        // Long-click functionality here if needed
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void searchMovies(String query) {
        db.collection("movies")
                .orderBy("title")
                .startAt(query)
                .endAt(query + "\uf8ff") // Ký tự đặc biệt để tìm kiếm toàn bộ khớp
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        movieList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            movieList.add(movie);
                        }
                        movieAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Lỗi khi tìm kiếm: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
