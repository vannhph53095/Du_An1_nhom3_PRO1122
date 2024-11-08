package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Database.DatabaseHelper;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.List_phimmoi_Adapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.viewPagerAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.PhimMoi;

public class TrangChu extends AppCompatActivity {
    private ViewPager mViewPager;
    private ImageView accout_ic;
    private RecyclerView recyclerView;
    private List_phimmoi_Adapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3); // Số 3 là số cột
        recyclerView.setLayoutManager(gridLayoutManager);

        // Khởi tạo DatabaseHelper và thêm dữ liệu nếu cần
        databaseHelper = new DatabaseHelper(this);
        if (databaseHelper.getAllPhim().isEmpty()) {
            databaseHelper.addPhim(new PhimMoi("Phim Avengers", R.drawable.iteam4));
            databaseHelper.addPhim(new PhimMoi("Phim Spider Man", R.drawable.mat_biec));
            databaseHelper.addPhim(new PhimMoi("Siu Pham", R.drawable.avenger));
        }

        // Lấy danh sách phim và thiết lập adapter
        List<PhimMoi> phimList = databaseHelper.getAllPhim();
        adapter = new List_phimmoi_Adapter(phimList);
        recyclerView.setAdapter(adapter);

        // Áp dụng WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo ViewPager
        int[] images = { R.drawable.iteam1, R.drawable.iteam2, R.drawable.iteam3, R.drawable.iteam4, R.drawable.iteam5 };
        mViewPager = findViewById(R.id.viewPagerMain);
        viewPagerAdapter mViewPagerAdapter = new viewPagerAdapter(this, images);
        mViewPager.setAdapter(mViewPagerAdapter);

        // Xử lý sự kiện click cho biểu tượng tài khoản
        accout_ic = findViewById(R.id.accout_ic);
        accout_ic.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChu.this, Manhinhadmin.class);
            startActivity(intent);
        });
    }
}
