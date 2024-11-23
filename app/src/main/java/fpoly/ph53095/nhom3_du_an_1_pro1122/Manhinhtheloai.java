package fpoly.ph53095.nhom3_du_an_1_pro1122;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MoviePagerAdapter;

public class Manhinhtheloai extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MoviePagerAdapter moviePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhtheloai);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        moviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(moviePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}