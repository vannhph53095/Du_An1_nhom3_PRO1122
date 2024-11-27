package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;  // Import Log
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class manhinhxemphim extends AppCompatActivity {

    private TextView tvTitle, tvGenre, tvDescription, tvDirector, tvYear;
    private RatingBar ratingBarMoviexp;
    private VideoView filmScreen;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhxemphim);

        btnBack = findViewById(R.id.btnbackinmanhinhyeuthich);
        tvTitle = findViewById(R.id.tvTitle);
        tvGenre = findViewById(R.id.tvGenre);
        tvDescription = findViewById(R.id.tvDescription);
        tvDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvDescription.getMaxLines() == 5) {
                    tvDescription.setMaxLines(Integer.MAX_VALUE);
                } else {
                    tvDescription.setMaxLines(5);
                }
            }
        });
        tvDirector = findViewById(R.id.tvDirector);
        tvYear = findViewById(R.id.tvYear);
        ratingBarMoviexp = findViewById(R.id.ratingBarMoviexp);
        filmScreen = findViewById(R.id.filmscreen);


        String title = getIntent().getStringExtra("title");
        String genre = getIntent().getStringExtra("genre");
        float rating = getIntent().getFloatExtra("rating", 0);
        String description = getIntent().getStringExtra("description");
        String director = getIntent().getStringExtra("director");
        int releaseYear = getIntent().getIntExtra("releaseYear", 0);
        String posterUri = getIntent().getStringExtra("posterUri");


        String shareLink = getIntent().getStringExtra("filmSource");


        Log.d("MovieInfo", "Title: " + title);
        Log.d("MovieInfo", "Genre: " + genre);
        Log.d("MovieInfo", "Rating: " + rating);
        Log.d("MovieInfo", "Description: " + description);
        Log.d("MovieInfo", "Director: " + director);
        Log.d("MovieInfo", "Release Year: " + releaseYear);
        Log.d("MovieInfo", "Poster URI: " + posterUri);
        Log.d("MovieInfo", "Share Link: " + shareLink);


        String videoId = null;
        if (shareLink != null && shareLink.contains("d/") && shareLink.contains("/view")) {
            try {

                videoId = shareLink.substring(shareLink.indexOf("d/") + 2, shareLink.indexOf("/view"));
                Log.d("VideoId", "Video ID: " + videoId);  // Log ID của video
            } catch (StringIndexOutOfBoundsException e) {
                e.printStackTrace();

                videoId = null;
                Log.e("Error", "Lỗi khi lấy videoId", e);  // Log lỗi
            }
        }

        if (videoId != null) {

            String mp4Url = "https://drive.google.com/uc?export=download&id=" + videoId;
            Log.d("MP4Url", "MP4 URL: " + mp4Url);  // Log URL MP4

            Uri videoUri = Uri.parse(mp4Url);
            filmScreen.setVideoURI(videoUri);


            tvTitle.setText(title);
            tvGenre.setText("  Thể loại:" + genre);
            tvDescription.setText("  Nội dung phim: " + description);
            tvDirector.setText("  Director: " + director);
            tvYear.setText("  Năm phát hành: " + releaseYear);
            ratingBarMoviexp.setRating(rating);


            Log.d("MovieDisplay", "Title set: " + title);
            Log.d("MovieDisplay", "Genre set: " + genre);
            Log.d("MovieDisplay", "Description set: " + description);
            Log.d("MovieDisplay", "Director set: " + director);
            Log.d("MovieDisplay", "Year set: " + releaseYear);
            Log.d("MovieDisplay", "Rating set: " + rating);


            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(filmScreen);
            filmScreen.setMediaController(mediaController);


            filmScreen.setOnPreparedListener(mp -> mp.setVolume(1.0f, 1.0f));


            filmScreen.start();
        } else {

            tvTitle.setText("Lỗi: Không thể tải video");
            Log.e("Error", "Video ID không hợp lệ");
        }


        btnBack.setOnClickListener(v -> onBackPressed());
    }
}
