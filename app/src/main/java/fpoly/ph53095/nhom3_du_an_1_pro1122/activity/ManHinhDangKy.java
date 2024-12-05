package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class ManHinhDangKy extends AppCompatActivity {

    private TextInputEditText edtEmail, edtPassword, edtConfirmPassword;
    private Button btnRegister, btnBackToLogin;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_ky);

        // Khởi tạo Firebase Auth và Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Tham chiếu đến các view
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        edtConfirmPassword = findViewById(R.id.repassword);
        btnRegister = findViewById(R.id.btndangky);
        btnBackToLogin = findViewById(R.id.btntrove);

        // Xử lý sự kiện khi nhấn nút đăng ký
        btnRegister.setOnClickListener(v -> registerUser());

        // Xử lý sự kiện khi nhấn nút quay lại đăng nhập
        btnBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ManHinhDangKy.this, ManHinhDangNhap.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        // Kiểm tra tính hợp lệ
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email không hợp lệ!");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            edtPassword.setError("Mật khẩu phải từ 6 ký tự trở lên!");
            return;
        }
        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Mật khẩu không khớp!");
            return;
        }

        // Tạo tài khoản với Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveUserToFirestore(email,password);
                    } else {
                        Toast.makeText(ManHinhDangKy.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserToFirestore(String email, String password) {
        String userId = mAuth.getCurrentUser().getUid();



        // Tạo dữ liệu người dùng
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("password", password); // Không khuyến khích
        user.put("hasPaid", false);
        user.put("timePaid", null);

        user.put("uid", userId);

        // Lưu vào Firestore
        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ManHinhDangKy.this, "Đăng ký thành công!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ManHinhDangKy.this, ManHinhDangNhap.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(ManHinhDangKy.this, "Lỗi khi lưu thông tin người dùng: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

}