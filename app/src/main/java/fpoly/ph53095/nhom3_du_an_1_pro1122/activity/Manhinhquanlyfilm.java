package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

public class Manhinhquanlyfilm extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {
    private LinearLayout addMovieLayout;
    private RecyclerView listquanly;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;
    private ImageView accout_ic, mhyeuthichbutton, home_icon;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhquanlyfilm);


        addMovieLayout = findViewById(R.id.add_movie);
        listquanly = findViewById(R.id.listquanly);
        email = getIntent().getStringExtra("email");
        home_icon=findViewById(R.id.home_icon);
        home_icon.setOnClickListener(v -> {
            Intent intent = new Intent(Manhinhquanlyfilm.this, TrangChu.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        });
        mhyeuthichbutton=findViewById(R.id.mhyeuthichbutton);
        mhyeuthichbutton.setOnClickListener(v -> {

            Intent intent = new Intent(Manhinhquanlyfilm.this, Manhinnhyeuthich.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        });

        accout_ic = findViewById(R.id.accout_ic);
        accout_ic.setOnClickListener(v -> {
            if ("vannhph53095@gmail.com".equals(email)) {
                Intent intent = new Intent(Manhinhquanlyfilm.this, Manhinhadmin.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();
        movieList = new ArrayList<>();


        movieAdapter = new MovieAdapter(this, movieList, this); // Truyền `this` cho callback
        listquanly.setAdapter(movieAdapter);
        listquanly.setLayoutManager(new GridLayoutManager(this, 2));

        loadMoviesFromFirestore();


        addMovieLayout.setOnClickListener(v -> {
            Intent intent = new Intent(Manhinhquanlyfilm.this, AddMovieActivity.class);
            startActivity(intent);
        });
    }

    private void loadMoviesFromFirestore() {
        db.collection("movies").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        movieList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);

                            movie.setId(document.getId());
                            movieList.add(movie);
                        }
                        movieAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Lỗi khi tải phim: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onMovieClick(Movie movie) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chỉnh sửa thông tin phim");


        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_movie, null);
        builder.setView(dialogView);

        EditText editFilmSource = dialogView.findViewById(R.id.editTextFilmSource);
        EditText editTitle = dialogView.findViewById(R.id.editTitle);
        EditText editGenre = dialogView.findViewById(R.id.editGenre);
        EditText editDescription = dialogView.findViewById(R.id.editDescription);
        EditText editAuthor = dialogView.findViewById(R.id.editAuthor);
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        ImageView imagePoster = dialogView.findViewById(R.id.imagePoster);

        editFilmSource.setText(movie.getFilmSource());
        editTitle.setText(movie.getTitle());
        editGenre.setText(movie.getGenre());
        editDescription.setText(movie.getDescription());
        editAuthor.setText(movie.getDirector());
        ratingBar.setRating((float) movie.getRating());


        if (movie.getPosterUri() != null && !movie.getPosterUri().isEmpty()) {
            Glide.with(this)
                    .load(movie.getPosterUri())
                    .into(imagePoster);

        }


        builder.setPositiveButton("Lưu", (dialog, which) -> {
            movie.setFilmSource(editFilmSource.getText().toString());
            movie.setTitle(editTitle.getText().toString());
            movie.setGenre(editGenre.getText().toString());
            movie.setDescription(editDescription.getText().toString());
            movie.setDirector(editAuthor.getText().toString());
            movie.setRating(ratingBar.getRating());


            db.collection("movies").document(movie.getId())
                    .set(movie)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Cập nhật phim thành công!", Toast.LENGTH_SHORT).show();
                        movieAdapter.notifyDataSetChanged();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi cập nhật phim!", Toast.LENGTH_SHORT).show();
                    });
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    @Override

    public void onMovieLongClick(Movie movie) {

        if (movie.getId() == null) {
            Toast.makeText(this, "Lỗi: ID của phim không xác định.", Toast.LENGTH_SHORT).show();
            return;
        }


        new AlertDialog.Builder(this)
                .setMessage("Bạn có chắc muốn xóa bộ phim này?")
                .setPositiveButton("Xóa", (dialog, which) -> {

                    db.collection("movies").document(movie.getId())
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                movieList.remove(movie);
                                movieAdapter.notifyDataSetChanged();
                                Toast.makeText(Manhinhquanlyfilm.this, "Xóa phim thành công!", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(Manhinhquanlyfilm.this, "Lỗi khi xóa phim!", Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
