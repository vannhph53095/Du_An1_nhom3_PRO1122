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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

public class MovieAdaptertop10 extends RecyclerView.Adapter<MovieAdaptertop10.Movietop10ViewHolder> {

    private List<Movie> movieList;
    private Context context;
    private MovieAdapter.OnMovieClickListener movieClickListener;
    public MovieAdaptertop10(Context context, List<Movie> movieList, OnMovieClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.movieClickListener = (MovieAdapter.OnMovieClickListener) listener;
    }

    @NonNull
    @Override
    public Movietop10ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movietop10, parent, false);
        return new Movietop10ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Movietop10ViewHolder holder, int position) {
        if (movieList == null || position >= movieList.size()) return;

        Movie movie = movieList.get(position);

        holder.txtMovieTitle.setText(movie.getTitle());

        Glide.with(context)
                .load(movie.getPosterUri())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageView);

        // Hiển thị trạng thái yêu thích
        holder.yeuthichbutton.setImageResource(movie.isLiked() ? R.drawable.heart_icon2 : R.drawable.heart_ic);

        // Xử lý click vào yêu thích
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
                    .addOnFailureListener(e -> Toast.makeText(context, "Lỗi khi cập nhật danh sách yêu thích!", Toast.LENGTH_SHORT).show());
        });

        // Xử lý click vào item
        holder.itemView.setOnClickListener(v -> {
            // Tăng lượt xem
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("movies").document(movie.getId())
                    .update("watched", FieldValue.increment(1))
                    .addOnSuccessListener(aVoid -> {
                        movie.setWatched(movie.Watched() + 1); // Đảm bảo có hàm getWatched()
                        Toast.makeText(context, "Bạn vừa xem: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                        if (movieClickListener != null) {
                            movieClickListener.onMovieClick(movie);
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(context, "Lỗi cập nhật lượt xem!", Toast.LENGTH_SHORT).show());
        });

        // Xử lý long click vào item
        holder.itemView.setOnLongClickListener(v -> {
            if (movieClickListener != null) {
                movieClickListener.onMovieLongClick(movie);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return (movieList != null) ? movieList.size() : 0;
    }

    public static class Movietop10ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtMovieTitle;
        public LinearLayout viewyeuthich;
        public ImageView imageView, yeuthichbutton;

        public Movietop10ViewHolder(@NonNull View itemView) {
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
