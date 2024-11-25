package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Manhinhlichsu;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class Manhinhclient extends AppCompatActivity {
    private Button btnlichsu;
    private ImageView btnBack, btnLogout, accoutIc;
    private String email;
    private TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manhinhclient);

        // Khởi tạo views
        initializeViews();

        // Lấy và hiển thị email
        handleEmailDisplay();

        // Thiết lập các sự kiện click
        setupClickListeners();

        // Thiết lập padding cho hệ thống
        setupSystemPadding();
    }

    private void initializeViews() {
        tvEmail = findViewById(R.id.tvemail);
        btnlichsu = findViewById(R.id.btnlichsu);
        btnBack = findViewById(R.id.btnbackinmanhinhyeuthich);
        btnLogout = findViewById(R.id.btnlogout);
        accoutIc = findViewById(R.id.accout_ic);
    }

    private void handleEmailDisplay() {
        // Lấy email từ Intent
        email = getIntent().getStringExtra("email");

        // Hiển thị email
        if (email != null && !email.isEmpty()) {
            tvEmail.setText(email);
        } else {
            tvEmail.setText("Email không xác định");
        }
    }

    private void setupClickListeners() {
        // Sự kiện cho nút Lịch sử
        btnlichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manhinhclient.this, Manhinhlichsu.class);
                intent.putExtra("email", email); // Truyền email sang màn hình lịch sử
                startActivity(intent);
            }
        });

        // Sự kiện cho nút Back
        btnBack.setOnClickListener(v -> onBackPressed());

        // Sự kiện cho nút Đăng xuất
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(Manhinhclient.this, ManHinhDangNhap.class);
            startActivity(intent);
            finish();
        });
    }

    private void setupSystemPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}