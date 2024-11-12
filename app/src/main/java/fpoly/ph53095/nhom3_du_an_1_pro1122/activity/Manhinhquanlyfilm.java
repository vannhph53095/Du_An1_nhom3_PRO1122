package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class Manhinhquanlyfilm extends AppCompatActivity {
    private RecyclerView listquanly;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manhinhquanlyfilm);

        listquanly = findViewById(R.id.listquanly);
        db = FirebaseFirestore.getInstance();

        // Cấu hình adapter và RecyclerView
        movieAdapter = new MovieAdapter(this, movieList);
        listquanly.setAdapter(movieAdapter);
        listquanly.setLayoutManager(new LinearLayoutManager(this));

        loadMoviesFromFirestore();


        ImageView icon_add = findViewById(R.id.icon_add);
        icon_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manhinhquanlyfilm.this, AddMovieActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadMoviesFromFirestore() {
        db.collection("movies").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        movieList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            movieList.add(movie);
                        }
                        movieAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Lỗi khi tải phim: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
