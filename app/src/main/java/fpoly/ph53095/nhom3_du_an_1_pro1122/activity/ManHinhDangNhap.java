package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class ManHinhDangNhap extends AppCompatActivity {

    private TextView btn_text_clickable;
    private EditText txtEmail, txtPassword;
    private Button btnDangNhap, btndenmanhinhdangky;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);

        // Ánh xạ các view
        btndenmanhinhdangky = findViewById(R.id.btndenmanhinhdangky);
        btn_text_clickable = findViewById(R.id.btn_text_clickable);
        txtEmail = findViewById(R.id.txtemail);
        txtPassword = findViewById(R.id.txtpassword);
        btnDangNhap = findViewById(R.id.btndangnhap);

        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Sự kiện "Quên mật khẩu"
        btn_text_clickable.setOnClickListener(v -> {
            Intent intent = new Intent(ManHinhDangNhap.this, KeoMatKhau.class);
            startActivity(intent);
        });

        // Sự kiện "Đăng ký"
        btndenmanhinhdangky.setOnClickListener(v -> {
            Intent intent = new Intent(ManHinhDangNhap.this, ManHinhDangKy.class);
            startActivity(intent);
        });

        // Xử lý đăng nhập
        btnDangNhap.setOnClickListener(v -> {
            String emailInput = txtEmail.getText().toString().trim();
            String passwordInput = txtPassword.getText().toString().trim();

            // Kiểm tra đầu vào
            if (emailInput.isEmpty()) {
                txtEmail.setError("Vui lòng nhập email");
                return;
            }
            if (passwordInput.isEmpty()) {
                txtPassword.setError("Vui lòng nhập mật khẩu");
                return;
            }

            // Firebase Authentication
            mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String uid = user.getUid();
                                String email = user.getEmail();
                                Toast.makeText(ManHinhDangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                handleLogin(email, uid);
                            }
                        } else {
                            Toast.makeText(ManHinhDangNhap.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    // Phương thức xử lý khi đăng nhập thành công
    private void handleLogin(String email, String uid) {
        Intent intent = new Intent(ManHinhDangNhap.this, TrangChu.class);
        intent.putExtra("email", email);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }
}
