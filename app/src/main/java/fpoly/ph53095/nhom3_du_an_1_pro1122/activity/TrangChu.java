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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdaptertop10;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.viewPagerAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

public class TrangChu extends AppCompatActivity implements MovieAdapter.OnMovieClickListener, MovieAdaptertop10.OnMovieClickListener {

    private RecyclerView recyclerView, recyclerViewtop10;
    private MovieAdapter movieAdapter;
    private MovieAdaptertop10 movieAdaptertop10;
    private List<Movie> movieList, movieListTop10;
    private FirebaseFirestore db;

    private String email, avatarUrl;
    private ImageView accountIcon, favoriteButton, homeIcon, categoryButton;
    private ViewPager viewPagerMain;
    private viewPagerAdapter pagerAdapter;

    private int currentPage = 0;
    private final int SLIDE_DELAY = 3000;
    private Handler sliderHandler = new Handler();
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        initializeUI(); // Khởi tạo giao diện và ánh xạ các thành phần
        initializeFirebase(); // Khởi tạo Firestore
        email=getIntent().getStringExtra("email");
        String uid = getIntent().getStringExtra("uid");
        if (uid != null) {
            checkPaymentStatus(uid);
        } else {
            Toast.makeText(this, "Không tìm thấy UID người dùng!", Toast.LENGTH_SHORT).show();
        }

        setupRecyclerViews();
        setupViewPager();
        setupListeners();
        loadMoviesFromFirestore();
        loadTop10MoviesFromFirestore();
    }

    private void initializeUI() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewtop10 = findViewById(R.id.recyclerViewtop10);
        viewPagerMain = findViewById(R.id.viewPagerMain);
        searchView = findViewById(R.id.searchView);
        accountIcon = findViewById(R.id.accout_ic);
        favoriteButton = findViewById(R.id.mhyeuthichbutton);
        homeIcon = findViewById(R.id.home_icon);
        categoryButton = findViewById(R.id.manhinhtheloaibtn);
    }

    private void initializeFirebase() {
        db = FirebaseFirestore.getInstance();
        movieList = new ArrayList<>();
        movieListTop10 = new ArrayList<>();
    }

    private void setupRecyclerViews() {
        movieAdapter = new MovieAdapter(this, movieList, this);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        movieAdaptertop10 = new MovieAdaptertop10(this, movieListTop10, this);
        recyclerViewtop10.setAdapter(movieAdaptertop10);
        recyclerViewtop10.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupViewPager() {
        int[] images = {R.drawable.iteam1, R.drawable.iteam2, R.drawable.iteam3};
        pagerAdapter = new viewPagerAdapter(this, images);
        viewPagerMain.setAdapter(pagerAdapter);
        sliderHandler.postDelayed(slideRunnable, SLIDE_DELAY);
    }

    private void setupListeners() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovies(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMovies(newText);
                return true;
            }
        });

        categoryButton.setOnClickListener(v -> startActivity(new Intent(TrangChu.this, Manhinhtheloai.class)));

        favoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChu.this, Manhinnhyeuthich.class);
            intent.putExtra("email", email);
            intent.putExtra("avatar", avatarUrl);
            startActivity(intent);
        });

        accountIcon.setOnClickListener(v -> {
            Intent intent;
            if ("vannhph53095@gmail.com".equals(email) || "anhtvph52826@gmail.com".equals(email) || "tunaph52894@gmail.com".equals(email)) {
                intent = new Intent(TrangChu.this, Manhinhadmin.class);
            } else {
                intent = new Intent(TrangChu.this, Manhinhclient.class);
            }
            intent.putExtra("email", email);
            intent.putExtra("avatar", avatarUrl);
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
                Toast.makeText(this, "Lỗi khi tải phim!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTop10MoviesFromFirestore() {
        db.collection("movies").orderBy("watched", Query.Direction.DESCENDING).limit(10).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                movieListTop10.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Movie movie = document.toObject(Movie.class);
                    movieListTop10.add(movie);
                }
                movieAdaptertop10.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Lỗi khi tải Top 10 phim!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchMovies(String query) {
        db.collection("movies").orderBy("title").startAt(query).endAt(query + "\uf8ff").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                movieList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Movie movie = document.toObject(Movie.class);
                    movieList.add(movie);
                }
                movieAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Lỗi khi tìm kiếm!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkPaymentStatus(String userId) {
        db.collection("users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Boolean hasPaid = documentSnapshot.getBoolean("hasPaid");
                if (hasPaid == null || !hasPaid) {
                    showPaymentDialog();
                }
            } else {
                Toast.makeText(this, "Không tìm thấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void showPaymentDialog() {
        Manhinhthanhtoan dialogFragment = new Manhinhthanhtoan();
        dialogFragment.show(getSupportFragmentManager(), "Manhinhthanhtoan");
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
        Intent intent = new Intent(TrangChu.this, manhinhxemphim.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void onMovieLongClick(Movie movie) {
        // Xử lý sự kiện giữ lâu trên phim
    }
}
