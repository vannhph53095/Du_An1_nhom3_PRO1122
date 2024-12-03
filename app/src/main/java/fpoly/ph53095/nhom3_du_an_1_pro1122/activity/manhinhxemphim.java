package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;  // Import Log
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Comment;

public class manhinhxemphim extends AppCompatActivity {

    private TextView tvTitle, tvGenre, tvDescription, tvDirector, tvYear;
    private RatingBar ratingBarMoviexp;
    private VideoView filmScreen;
    private ImageView btnBack;

    Button btnSendComment;
    DatabaseReference mDatabase;

    List<Comment> comments;
    CommentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhxemphim);

        btnBack = findViewById(R.id.btnbackinmanhinhyeuthich);
        tvTitle = findViewById(R.id.tvTitle);
        tvGenre = findViewById(R.id.tvGenre);
        tvDescription = findViewById(R.id.tvDescription);
        btnSendComment = findViewById(R.id.btnSendComment);
        //region sss
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
            //viết ra email m đi tunaph52894@gmail.com
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
        //endregion
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String titleeeee = getIntent().getStringExtra("title");
        mDatabase = FirebaseDatabase.getInstance().getReference("comments").child(titleeeee);

        RecyclerView listbinhluan = findViewById(R.id.listbinhluan);
        EditText etComment = findViewById(R.id.etComment);
        Button btnSendComment = findViewById(R.id.btnSendComment);
        loadData();
        comments = new ArrayList<>();
        adapter = new CommentAdapter(comments);
        listbinhluan.setLayoutManager(new LinearLayoutManager(this));
        listbinhluan.setAdapter(adapter);



        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = etComment.getText().toString();
                if (!commentText.isEmpty()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        String username = user.getEmail();
                        String movieTitle = tvTitle.getText().toString();
                        Comment newComment = new Comment(username, commentText, movieTitle);
                        DatabaseReference movieCommentsRef = mDatabase.child(movieTitle);
                        String commentId = movieCommentsRef.push().getKey();
                        movieCommentsRef.child(commentId).setValue(newComment)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        comments.add(newComment);
//                                        adapter.notifyItemInserted(comments.size() - 1);
                                        loadData();
                                        etComment.setText("");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(manhinhxemphim.this, "Failed to add comment", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(manhinhxemphim.this, "Bạn cần đăng nhập để bình luận", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    void loadData(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.clear();
                for (DataSnapshot movieSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot commentSnapshot : movieSnapshot.getChildren()) {
                        Comment comment = commentSnapshot.getValue(Comment.class);
                        comments.add(comment);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("manhinhxemphim", "loadComments:onCancelled", databaseError.toException());
                Toast.makeText(manhinhxemphim.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
