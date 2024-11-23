package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Manhinhlichsu;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class Manhinhclient extends AppCompatActivity {
private Button btnlichsu;
    private ImageView btnBack, btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manhinhclient);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnlichsu=findViewById(R.id.btnlichsu);
        btnBack = findViewById(R.id.btnbackinmanhinhyeuthich);
        btnLogout = findViewById(R.id.btnlogout);
        btnlichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manhinhclient.this, Manhinhlichsu.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(v -> {
            onBackPressed(); // Quay lại màn hình trước
        });
        // Đăng xuất
        btnLogout.setOnClickListener(v -> {
            // Xử lý đăng xuất, có thể sử dụng Firebase Auth nếu bạn đang dùng
            Intent intent = new Intent(Manhinhclient.this, ManHinhDangNhap.class); // Quay lại màn hình đăng nhập
            startActivity(intent);
            finish(); // Kết thúc màn hình admin
        });
    }
}