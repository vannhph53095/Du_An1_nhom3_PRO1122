package fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.Movie;
import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.txtMovieTitle.setText(movie.getTitle());
        holder.txtMovieGenre.setText(movie.getGenre());
        holder.ratingBarMovie.setRating(movie.getRating());

        // Sử dụng Glide để tải hình ảnh
        Glide.with(context)
                .load(movie.getPosterResId()) // Nếu bạn có URL, hãy thay thế bằng URL
                .into(holder.imageView);

        // Thiết lập sự kiện click cho hình ảnh
        holder.imageView.setOnClickListener(v -> showMovieOptions(movie));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    private void showMovieOptions(Movie movie) {
        // Tạo dialog để hiển thị các tùy chọn
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(movie.getTitle())
                .setItems(new CharSequence[]{"Xem Phim", "Thông Tin Phim"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            // Xử lý xem phim
                            Toast.makeText(context, "Chức năng 'Xem Phim' chưa được triển khai.", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            // Hiển thị thông tin phim
                            showMovieInfo(movie);
                            break;
                    }
                })
                .show();
    }
    private void showMovieInfo(Movie movie) {
        // Tạo dialog để hiển thị thông tin phim
        AlertDialog.Builder infoBuilder = new AlertDialog.Builder(context);
        infoBuilder.setTitle(movie.getTitle())
                .setMessage("Thể loại: " + movie.getGenre() + "\n" +
                        "Đánh giá: " + movie.getRating() + "\n" +
                        "Đạo diễn: " + movie.getDirector() + "\n" +
                        "Năm phát hành: " + movie.getReleaseYear() + "\n" +
                        "Mô tả: " + movie.getDescription())
                .setPositiveButton("OK", null)
                .show();
    }
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView txtMovieTitle;
        public TextView txtMovieGenre;
        public RatingBar ratingBarMovie;
        public ImageView imageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            txtMovieTitle = itemView.findViewById(R.id.txtMovieTitle);
            txtMovieGenre = itemView.findViewById(R.id.txtMovieGenre);
            ratingBarMovie = itemView.findViewById(R.id.ratingBarMovie);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}