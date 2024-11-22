package fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.activity.manhinhxemphim;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

import fpoly.ph53095.nhom3_du_an_1_pro1122.activity.AddMovieActivity;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private Context context;
    private OnMovieClickListener movieClickListener;


    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
        void onMovieLongClick(Movie movie);
    }


    public MovieAdapter(Context context, List<Movie> movieList, OnMovieClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.movieClickListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.txtMovieTitle.setText(movie.getTitle());

        Glide.with(context)
                .load(movie.getPosterUri()) // Giả sử bạn có một URI hình ảnh
                .into(holder.imageView);
        // Khi click vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, manhinhxemphim.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("genre", movie.getGenre());
            intent.putExtra("rating", movie.getRating());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("director", movie.getDirector());
            intent.putExtra("releaseYear", movie.getReleaseYear());
            intent.putExtra("filmSource", movie.getFilmSource());
            intent.putExtra("posterUri", movie.getPosterUri());
            context.startActivity(intent);

        });

        // Khi long-click vào item (giống như xóa hoặc sửa)
        holder.itemView.setOnLongClickListener(v -> {
            if (movieClickListener != null) {
                movieClickListener.onMovieLongClick(movie);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView txtMovieTitle;
        public TextView txtMovieGenre;
        public RatingBar ratingBarMovie;
        public ImageView imageView;
        public MovieViewHolder(View itemView) {
            super(itemView);
            txtMovieTitle = itemView.findViewById(R.id.txtMovieTitle);

            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
