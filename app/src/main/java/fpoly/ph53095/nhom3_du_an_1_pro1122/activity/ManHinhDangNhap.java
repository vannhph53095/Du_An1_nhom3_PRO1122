package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;




import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class ManHinhDangNhap extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private Button btnDangNhap, accountIcon;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);

        // Khởi tạo các thành phần UI
        txtEmail = findViewById(R.id.txtemail);
        txtPassword = findViewById(R.id.txtpassword);
        btnDangNhap = findViewById(R.id.btndangnhap);


        mAuth = FirebaseAuth.getInstance();

        // Xử lý sự kiện nhấn nút Đăng nhập
        btnDangNhap.setOnClickListener(v -> {
            String emailInput = txtEmail.getText().toString().trim();
            String passwordInput = txtPassword.getText().toString().trim();

            if (emailInput.isEmpty()) {
                txtEmail.setError("Vui lòng nhập email");
                return;
            }
            if (passwordInput.isEmpty()) {
                txtPassword.setError("Vui lòng nhập mật khẩu");
                return;
            }

            mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ManHinhDangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            handleLogin(emailInput, passwordInput);
                        } else {
                            Toast.makeText(ManHinhDangNhap.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Xử lý sự kiện nhấn vào biểu tượng tài khoản

    }

    // Phương thức xử lý đăng nhập và chuyển hướng
    private void handleLogin(String emailInput, String passwordInput) {
        if (emailInput.equals("vannhph53095@gmail.com") && passwordInput.equals("1234567")) {
            Intent intent = new Intent(ManHinhDangNhap.this, TrangChu.class);
            intent.putExtra("email", emailInput);  // Chuyền email vào TrangChu
            startActivity(intent);
        } else {
            Intent intent = new Intent(ManHinhDangNhap.this, TrangChu.class);
            intent.putExtra("email", emailInput);  // Chuyền email vào TrangChu
            startActivity(intent);
        }
        finish();
    }
}
