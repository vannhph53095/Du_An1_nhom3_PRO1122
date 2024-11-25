package fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.activity.AddMovieActivity;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private Context context;
    private OnMovieClickListener movieClickListener;

    // Constructor với đối số cho context, danh sách phim, và listener sự kiện click
    public MovieAdapter(Context context, List<Movie> movieList, OnMovieClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.movieClickListener = listener;
    }

    public MovieAdapter(ArrayList<Movie> movieList, AddMovieActivity addMovieActivity) {
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        // Gán tiêu đề phim vào TextView
        holder.txtMovieTitle.setText(movie.getTitle());

        // Load hình ảnh bằng Glide từ URI của phim
        Glide.with(context)
                .load(movie.getPosterUri())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageView);
        if (movie.isLiked()) {
            holder.yeuthichbutton.setImageResource(R.drawable.heart_icon2);
        } else {
            holder.yeuthichbutton.setImageResource(R.drawable.heart_ic);
        }


        holder.viewyeuthich.setOnClickListener(v -> {
            boolean isCurrentlyLiked = movie.isLiked();
            movie.setLiked(!isCurrentlyLiked);
            notifyItemChanged(position);


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("movies").document(movie.getId())
                    .update("isLiked", !isCurrentlyLiked)
                    .addOnSuccessListener(aVoid -> {
                        holder.yeuthichbutton.setImageResource(!isCurrentlyLiked ? R.drawable.heart_icon2 : R.drawable.heart_ic);
                        Toast.makeText(context, !isCurrentlyLiked ? "Đã thêm vào danh sách yêu thích!" : "Đã xóa khỏi danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Lỗi khi cập nhật danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                    });
        });
        holder.itemView.setOnClickListener(v -> {
            // Tăng số lượng watched
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("movies").document(movie.getId())
                    .update("watched", movie.getWatched() + 1)
                    .addOnSuccessListener(aVoid -> {
                        movie.setWatched(movie.getWatched() + 1);
                        Toast.makeText(context, "Bạn vừa xem: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                        if (movieClickListener != null) {
                            movieClickListener.onMovieClick(movie);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Lỗi cập nhật lượt xem!", Toast.LENGTH_SHORT).show();
                    });
        });

        // Xử lý sự kiện click vào item
        holder.itemView.setOnClickListener(v -> {
            if (movieClickListener != null) {
                movieClickListener.onMovieClick(movie);
            }
        });

        // Xử lý sự kiện long click vào item
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
        public LinearLayout viewyeuthich;
        public ImageView imageView, yeuthichbutton;

        public MovieViewHolder(View itemView) {
            super(itemView);
            txtMovieTitle = itemView.findViewById(R.id.txtMovieTitle);
            viewyeuthich = itemView.findViewById(R.id.viewyeuthich);
            imageView = itemView.findViewById(R.id.imageView);
            yeuthichbutton = itemView.findViewById(R.id.yeuthichbutton);
        }
    }

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
        void onMovieLongClick(Movie movie);
    }
}
