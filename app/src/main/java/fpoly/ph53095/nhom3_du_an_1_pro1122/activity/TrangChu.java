package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Database.DatabaseHelper;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.List_phimmoi_Adapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Movie;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.viewPagerAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.SpacingItemDecoration;

public class TrangChu extends AppCompatActivity {
    private ViewPager mViewPager;
    private ImageView accout_ic;
    private RecyclerView recyclerView;
    private List_phimmoi_Adapter adapter;
    private DatabaseHelper databaseHelper;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        recyclerView = findViewById(R.id.recyclerView);
        movieList = new ArrayList<>();

        // Thêm dữ liệu vào movieList
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("Phim A", "Hành động", 4.5f, R.drawable.hary_potter,
                "Mô tả phim A", "Đạo diễn A", 2023));
        movieList.add(new Movie("Phim B", "Hài", 4.0f, R.drawable.iteam4,
                "Mô tả phim B", "Đạo diễn B", 2022));
        movieList.add(new Movie("Phim C", "Hài", 5.0f, R.drawable.iteam1,
                "Mô tả phim B", "Đạo diễn C", 2022));
        // Thêm nhiều phim hơn nếu cần

        movieAdapter = new MovieAdapter(this, movieList);
        recyclerView.setAdapter(movieAdapter);

        // Thiết lập GridLayoutManager với 2 cột
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Thêm ItemDecoration để tạo khoảng cách giữa các item
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing); // Đặt kích thước khoảng cách
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

    }
}
