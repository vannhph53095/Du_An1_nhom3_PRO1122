package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Database.DatabaseHelper;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.List_phimmoi_Adapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.viewPagerAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.SpacingItemDecoration;

public class TrangChu extends AppCompatActivity {
    private ViewPager mViewPager;
    private ImageView accout_ic;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        recyclerView = findViewById(R.id.recyclerView);
        db = FirebaseFirestore.getInstance();
        movieList = new ArrayList<>();


        movieAdapter = new MovieAdapter(this, movieList);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacingItemDecoration(spacingInPixels));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        int[] images = {R.drawable.iteam1, R.drawable.iteam2, R.drawable.iteam3, R.drawable.iteam4, R.drawable.iteam5};
        mViewPager = findViewById(R.id.viewPagerMain);
        viewPagerAdapter mViewPagerAdapter = new viewPagerAdapter(this, images);
        mViewPager.setAdapter(mViewPagerAdapter);


        accout_ic = findViewById(R.id.accout_ic);
        accout_ic.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChu.this, Manhinhadmin.class);
            startActivity(intent);
        });

        loadMoviesFromFirestore();
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
}
