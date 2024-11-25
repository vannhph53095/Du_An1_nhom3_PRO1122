package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Manhinhlichsu;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class Manhinhadmin extends AppCompatActivity {
    private ImageView accout_ic, mhyeuthichbutton, home_icon;
    private String email;
    private Button  btnlichsuadmin;
    private ImageView btnBack, btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manhinhadmin);
        Button btnquanly = findViewById(R.id.btnquanlyfilm);
        btnBack = findViewById(R.id.btnbackinmanhinhyeuthich);
        btnLogout = findViewById(R.id.btnlogout);
        btnquanly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manhinhadmin.this, Manhinhquanlyfilm.class);
                intent.putExtra("email", email);
                startActivity(intent);


            }
        });
        email = getIntent().getStringExtra("email");
        home_icon=findViewById(R.id.home_icon);
        home_icon.setOnClickListener(v -> {
            Intent intent = new Intent(Manhinhadmin.this, TrangChu.class);
            intent.putExtra("email", email);
            startActivity(intent);

        });
        mhyeuthichbutton=findViewById(R.id.mhyeuthichbutton);
        mhyeuthichbutton.setOnClickListener(v -> {

            Intent intent = new Intent(Manhinhadmin.this, Manhinnhyeuthich.class);
            intent.putExtra("email", email);
            startActivity(intent);

        });

        accout_ic = findViewById(R.id.accout_ic);
        accout_ic.setOnClickListener(v -> {
            if ("vannhph53095@gmail.com".equals(email) || "anhtvph52826@gmail.com".equals(email) || "tunaph52894@gmail.com".equals(email) ) {
                Intent intent = new Intent(Manhinhadmin.this, Manhinhadmin.class);
                intent.putExtra("email", email);
                startActivity(intent);

            }
        });
        btnlichsuadmin=findViewById(R.id.lichsuadmin);
        btnlichsuadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manhinhadmin.this, Manhinhlichsu.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(v -> {
            onBackPressed(); // Quay lại màn hình trước
        });
        // Đăng xuất
        btnLogout.setOnClickListener(v -> {
            // Xử lý đăng xuất, có thể sử dụng Firebase Auth nếu bạn đang dùng
            Intent intent = new Intent(Manhinhadmin.this, ManHinhDangNhap.class); // Quay lại màn hình đăng nhập
            startActivity(intent);
            finish(); // Kết thúc màn hình admin
        });
    }
}