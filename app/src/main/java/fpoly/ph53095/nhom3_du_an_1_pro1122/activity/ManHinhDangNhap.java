package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class ManHinhDangNhap extends AppCompatActivity {

    private TextView btnForgotPassword;
    private EditText txtEmail, txtPassword;
    private Button btnLogin, btnRegister;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);


        // Ánh xạ
        btnForgotPassword = findViewById(R.id.btn_text_clickable);
        txtEmail = findViewById(R.id.txtemail);
        txtPassword = findViewById(R.id.txtpassword);
        btnLogin = findViewById(R.id.btndangnhap);
        btnRegister = findViewById(R.id.btndenmanhinhdangky);

        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Quên mật khẩu
        btnForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(ManHinhDangNhap.this, KeoMatKhau.class);
            startActivity(intent);
        });

        // Đăng ký
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(ManHinhDangNhap.this, ManHinhDangKy.class);
            startActivity(intent);
        });

        // Đăng nhập
        btnLogin.setOnClickListener(v -> {
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String email = user.getEmail();
                                Log.d("DEBUG_EMAIL", "Đăng nhập thành công với email: " + email);
                                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();




                                handleLogin(email);
                            }
                        } else {
                            Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }




    private void handleLogin(String email) {
        Intent intent = new Intent(ManHinhDangNhap.this, TrangChu.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }
}
