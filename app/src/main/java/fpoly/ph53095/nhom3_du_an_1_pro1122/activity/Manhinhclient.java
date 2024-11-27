package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Manhinhlichsu;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class Manhinhclient extends AppCompatActivity {
    private Button btnlichsu, btnChangeAvatar;
    private ImageView btnBack, btnLogout, accoutIc, clientavatar;
    private String email;
    private TextView tvEmail;

    private static final int PICK_IMAGE_REQUEST = 1; // Request code để chọn ảnh

    private static final String PREFS_NAME = "ClientPrefs";
    private static final String KEY_AVATAR_URI = "avatar_uri";

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
        String savedUri = getSavedAvatarUri();
        if (savedUri != null) {
            Glide.with(this)
                    .load(Uri.parse(savedUri))
                    .circleCrop()
                    .into(clientavatar);
        }

        // Xử lý sự kiện cho nút "Change Avatar"
        btnChangeAvatar.setOnClickListener(v -> openImagePicker());
    }

    private void initializeViews() {
        tvEmail = findViewById(R.id.tvemail);
        btnlichsu = findViewById(R.id.btnlichsu);
        btnBack = findViewById(R.id.btnbackinmanhinhyeuthich);
        btnLogout = findViewById(R.id.btnlogout);
        accoutIc = findViewById(R.id.accout_ic);
        clientavatar = findViewById(R.id.clientavatar);
        btnChangeAvatar = findViewById(R.id.btnChangeAvatar);
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
        btnlichsu.setOnClickListener(v -> {
            Intent intent = new Intent(Manhinhclient.this, Manhinhlichsu.class);
            intent.putExtra("email", email); // Truyền email sang màn hình lịch sử
            startActivity(intent);
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

    // Mở trình chọn ảnh
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            // Hiển thị ảnh hình tròn
            Glide.with(this)
                    .load(selectedImageUri)
                    .circleCrop()
                    .into(clientavatar);

            // Lưu URI vào SharedPreferences
            saveAvatarUri(selectedImageUri.toString());

            Toast.makeText(this, "Ảnh đã được chọn và lưu!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Không chọn được ảnh!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAvatarUri(String uri) {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(KEY_AVATAR_URI, uri)
                .apply();
    }

    private String getSavedAvatarUri() {
        return getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .getString(KEY_AVATAR_URI, null);
    }
}
