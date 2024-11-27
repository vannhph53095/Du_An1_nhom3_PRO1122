package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MoviePagerAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class Manhinhtheloai extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MoviePagerAdapter moviePagerAdapter;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhtheloai);


        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);


        moviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(moviePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        btnBack = findViewById(R.id.btnbackinmanhinhyeuthich);
        btnBack.setOnClickListener(v -> {
           onBackPressed(); // Quay lại màn hình trước
        });
    }
}