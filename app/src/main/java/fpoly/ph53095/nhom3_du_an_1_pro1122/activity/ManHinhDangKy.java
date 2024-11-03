package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class ManHinhDangKy extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btndangky, btntrove;
    private TextInputEditText email, password, repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_dang_ky);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btndangky = findViewById(R.id.btndangky);
        btntrove = findViewById(R.id.btntrove);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        mAuth = FirebaseAuth.getInstance();


        btndangky.setOnClickListener(v -> {
            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();
            String repasswordInput = repassword.getText().toString().trim();


            if (!passwordInput.equals(repasswordInput)) {
                repassword.setError("Mật khẩu không khớp");
                return;
            } else if (emailInput.isEmpty()) {
                email.setError("Vui lòng nhập email");
                return;
            } else if (passwordInput.isEmpty()) {
                password.setError("Vui lòng nhập mật khẩu");
                return;
            } else if (repasswordInput.isEmpty()) {
                repassword.setError("Vui lòng nhập lại mật khẩu");
                return;
            } else if (passwordInput.length() < 6) {
                password.setError("Mật khẩu phải có ít nhất 6 ký tự");
                return;
            }


            mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(ManHinhDangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ManHinhDangKy.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });


        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManHinhDangKy.this, ManHinhDangNhap.class);
                startActivity(intent);
            }
        });
    }
}
