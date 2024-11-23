package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.MovieAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

public class MovieFragment extends Fragment {
    private static final String ARG_GENRE = "genre";
    private RecyclerView recyclerView;
    private String genre;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();

    public static MovieFragment newInstance(String genre) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GENRE, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_movie_fragment, container, false);

        if (getArguments() != null) {
            genre = getArguments().getString(ARG_GENRE);
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo adapter ngay từ đầu (dù dữ liệu chưa có)
        movieAdapter = new MovieAdapter(getContext(), movieList, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = new Intent(MovieFragment.this.getContext(), manhinhxemphim.class);
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("genre", movie.getGenre());
                intent.putExtra("description", movie.getDescription());
                intent.putExtra("director", movie.getDirector());
                intent.putExtra("releaseYear", movie.getReleaseYear());
                intent.putExtra("rating", movie.getRating());
                intent.putExtra("filmSource", movie.getFilmSource());
                intent.putExtra("posterUri", movie.getPosterUri());
                startActivity(intent);
            }

            @Override
            public void onMovieLongClick(Movie movie) {
                // Xử lý sự kiện long click vào phim
            }
        });

        recyclerView.setAdapter(movieAdapter);

        // Lấy danh sách phim theo thể loại
        getMoviesByGenre(genre);

        return view;
    }

    private void getMoviesByGenre(String genre) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("movies")
                .whereEqualTo("genre", genre)
                .get()
                .addOnCompleteListener(task -> {
                    Log.d("MovieFragment", "Genre passed: " + genre);

                    if (task.isSuccessful()) {
                        movieList.clear(); // Xóa dữ liệu cũ
                        for (DocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            if (movie != null) {
                                movieList.add(movie);
                                Log.d("MovieFragment", "Movie added: " + movie.getTitle()); // Log title of added movie
                            }
                        }
                        Log.d("MovieFragment", "Total movies found: " + movieList.size());
                        // Gọi notifyDataSetChanged() sau khi dữ liệu được cập nhật
                        if (movieAdapter != null) {
                            movieAdapter.notifyDataSetChanged();
                        }
                    } else {
                        // Xử lý trường hợp lỗi
                        Log.e("MovieFragment", "Error getting movies: ", task.getException());
                    }
                });
    }
}
