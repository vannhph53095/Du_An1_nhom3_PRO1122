package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class manhinhxemphim extends AppCompatActivity {
    private VideoView filmscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhxemphim);

        filmscreen = findViewById(R.id.filmscreen);

        // Nhận tên video từ Intent
        String filmSource = getIntent().getStringExtra("filmSource");

        if (filmSource != null && !filmSource.isEmpty()) {
            // Lấy ID tài nguyên video từ thư mục raw
            int videoResId = getResources().getIdentifier(filmSource, "raw", getPackageName());

            if (videoResId != 0) {
                // Tạo URI cho VideoView
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
                filmscreen.setVideoURI(uri);

                // Tạo và thiết lập MediaController để điều khiển video
                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(filmscreen);
                filmscreen.setMediaController(mediaController);

                // Bắt đầu phát video
                filmscreen.start();
            } else {
                Toast.makeText(this, "Không tìm thấy video trong tài nguyên.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Tên video không hợp lệ.", Toast.LENGTH_SHORT).show();
        }
    }


}
