package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class KeoMatKhau extends AppCompatActivity {
    private EditText edtEmail;
    private TextView errorMessage;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_keo_mat_khau);
        mAuth = FirebaseAuth.getInstance();


        edtEmail = findViewById(R.id.edtEmail);
        errorMessage = findViewById(R.id.errorMessage);
        Button submitButton = findViewById(R.id.submitButton);
        TextView backToLogin = findViewById(R.id.backToLogin);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    errorMessage.setText("Vui lòng nhập email.");
                    errorMessage.setVisibility(View.VISIBLE);
                } else {
                    resetPassword(email);
                }
            }
        });

        // Xử lý sự kiện khi nhấn vào "Quay lại đăng nhập"
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Điều hướng về trang đăng nhập
                Intent intent = new Intent(KeoMatKhau.this, ManHinhDangNhap.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void resetPassword(String email) {
        // Gửi email đặt lại mật khẩu qua Firebase
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(KeoMatKhau.this, "Đã gửi email đặt lại mật khẩu. Kiểm tra hộp thư của bạn.", Toast.LENGTH_SHORT).show();
                        errorMessage.setVisibility(View.GONE); // Ẩn thông báo lỗi nếu có
                    } else {
                        errorMessage.setText("Lỗi: Không thể gửi email đặt lại mật khẩu.");
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                });
    }
}