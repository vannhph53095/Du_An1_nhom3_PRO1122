package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class Manhinhadmin extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1; // Request code để chọn ảnh

    private ImageView accout_ic, mhyeuthichbutton, home_icon, btnBack, btnLogout, adminAvatar;
    private Button btnChangeImg,  btnQuanLyFilm;
    private String email;
    private static final String PREFS_NAME = "AdminPrefs";
    private static final String KEY_AVATAR_URI = "avatar_uri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhadmin);

        // Ánh xạ các thành phần giao diện
        btnQuanLyFilm = findViewById(R.id.btnquanlyfilm);
        btnBack = findViewById(R.id.btnbackinmanhinhyeuthich);
        btnLogout = findViewById(R.id.btnlogout);

        adminAvatar = findViewById(R.id.adminavatar);
        btnChangeImg = findViewById(R.id.changeimg);
        home_icon = findViewById(R.id.home_icon);
        mhyeuthichbutton = findViewById(R.id.mhyeuthichbutton);
        accout_ic = findViewById(R.id.accout_ic);

        // Lấy email từ Intent
        email = getIntent().getStringExtra("email");

        // Tải ảnh từ SharedPreferences nếu có
        String savedUri = getSavedAvatarUri();
        if (savedUri != null) {
            Glide.with(this)
                    .load(Uri.parse(savedUri))
                    .circleCrop()
                    .into(adminAvatar);
        } else {
            String avatarUrl = getIntent().getStringExtra("avatar");
            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                Glide.with(this)
                        .load(avatarUrl)
                        .circleCrop()
                        .into(adminAvatar);
            } else {
                Toast.makeText(this, "Không có avatar!", Toast.LENGTH_SHORT).show();
            }
        }

        // Xử lý sự kiện cho nút "Quản Lý Film"
        btnQuanLyFilm.setOnClickListener(v -> {
            Intent intent = new Intent(Manhinhadmin.this, Manhinhquanlyfilm.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });

        // Xử lý sự kiện cho nút "Back"
        btnBack.setOnClickListener(v -> {
            onBackPressed(); // Quay lại màn hình trước
        });

        // Xử lý sự kiện cho nút "Đăng Xuất"
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(Manhinhadmin.this, ManHinhDangNhap.class);
            startActivity(intent);
            finish(); // Kết thúc màn hình admin
        });

        // Xử lý sự kiện cho biểu tượng "Trang Chủ"
        home_icon.setOnClickListener(v -> {
            Intent intent = new Intent(Manhinhadmin.this, TrangChu.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });

        // Xử lý sự kiện cho nút "Màn Hình Yêu Thích"
        mhyeuthichbutton.setOnClickListener(v -> {
            Intent intent = new Intent(Manhinhadmin.this, Manhinnhyeuthich.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });

        // Xử lý sự kiện cho nút chọn ảnh
        btnChangeImg.setOnClickListener(v -> openImagePicker());

        // Xử lý sự kiện cho biểu tượng tài khoản
        accout_ic.setOnClickListener(v -> {
            if ("vannhph53095@gmail.com".equals(email) ||
                    "anhtvph52826@gmail.com".equals(email) ||
                    "tunaph52894@gmail.com".equals(email)) {
                Intent intent = new Intent(Manhinhadmin.this, Manhinhadmin.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
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
                    .into(adminAvatar);

            // Lưu URI vào SharedPreferences
            saveAvatarUri(selectedImageUri.toString());

            Toast.makeText(this, "Ảnh đã được chọn và lưu!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Không chọn được ảnh!", Toast.LENGTH_SHORT).show();
        }
    }
}
