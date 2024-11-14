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

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class ManHinhDangNhap extends AppCompatActivity {
private TextView btn_text_clickable;
    private EditText txtEmail, txtPassword;
    private Button btnDangNhap, accountIcon,btndenmanhinhdangky;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);
btndenmanhinhdangky=findViewById(R.id.btndenmanhinhdangky);
        btn_text_clickable=findViewById(R.id.btn_text_clickable);
        btn_text_clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManHinhDangNhap.this, KeoMatKhau.class);
                startActivity(intent);
            }
        });
        btndenmanhinhdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManHinhDangNhap.this, ManHinhDangKy.class);
                startActivity(intent);
            }
        });
        txtEmail = findViewById(R.id.txtemail);
        txtPassword = findViewById(R.id.txtpassword);
        btnDangNhap = findViewById(R.id.btndangnhap);


        mAuth = FirebaseAuth.getInstance();


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



    }


    private void handleLogin(String emailInput, String passwordInput) {
        if (emailInput.equals("vannhph53095@gmail.com") && passwordInput.equals("1234567")) {
            Intent intent = new Intent(ManHinhDangNhap.this, TrangChu.class);
            intent.putExtra("email", emailInput);
            startActivity(intent);
        } else {
            Intent intent = new Intent(ManHinhDangNhap.this, TrangChu.class);
            intent.putExtra("email", emailInput);
            startActivity(intent);
        }
        finish();
    }
}
