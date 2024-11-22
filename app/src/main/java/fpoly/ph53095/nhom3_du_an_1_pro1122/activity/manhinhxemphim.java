package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class manhinhxemphim extends AppCompatActivity {

    private TextView tvTitle, tvGenre, tvDescription, tvDirector, tvYear;
    private RatingBar ratingBarMoviexp;
    private VideoView filmScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhxemphim);

        // Ánh xạ view
        tvTitle = findViewById(R.id.tvTitle);
        tvGenre = findViewById(R.id.tvGenre);
        tvDescription = findViewById(R.id.tvDescription);
        tvDirector = findViewById(R.id.tvDirector);
        tvYear = findViewById(R.id.tvYear);
        ratingBarMoviexp = findViewById(R.id.ratingBarMoviexp);
        filmScreen = findViewById(R.id.filmscreen);


        // Nhận dữ liệu từ Intent
        String title = getIntent().getStringExtra("title");
        String genre = getIntent().getStringExtra("genre");
        float rating = getIntent().getFloatExtra("rating", 0);
        String description = getIntent().getStringExtra("description");
        String director = getIntent().getStringExtra("director");
        int releaseYear = getIntent().getIntExtra("releaseYear", 0);
        String filmSource = getIntent().getStringExtra("filmSource");
        String posterUri = getIntent().getStringExtra("posterUri");

        tvTitle.setText(title);
        tvGenre.setText("  Thể loại:"+genre);
        tvDescription.setText("  Nội dung phim: "+description);
        tvDirector.setText("  Director: " + director);
        tvYear.setText("  Năm phát hành : " + releaseYear);
        ratingBarMoviexp.setRating(rating);

        // Hiển thị video
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + filmSource);
        filmScreen.setVideoURI(videoUri);
        filmScreen.start();


    }
}
