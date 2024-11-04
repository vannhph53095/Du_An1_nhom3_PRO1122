package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Manhinhadmin;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.viewPagerAdapter;

public class TrangChu extends AppCompatActivity {
    private ViewPager mViewPager;
ImageView accout_ic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        int[] images = {
                R.drawable.iteam1, R.drawable.iteam2, R.drawable.iteam3, R.drawable.iteam4, R.drawable.iteam5
        };


        mViewPager = findViewById(R.id.viewPagerMain);
        viewPagerAdapter mViewPagerAdapter = new viewPagerAdapter(this, images);
        mViewPager.setAdapter(mViewPagerAdapter);
        accout_ic=findViewById(R.id.accout_ic);
        accout_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TrangChu.this, Manhinhadmin.class);
                startActivity(intent);
            }
        });
    }
}