package fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private Context context;
    private FirebaseFirestore db;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflating layout item_movie.xml mà bạn đã tạo
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        // Bind data vào layout
        holder.txtMovieTitle.setText(movie.getTitle());
        holder.txtMovieGenre.setText(movie.getGenre());
        holder.ratingBarMovie.setRating(movie.getRating());

        Glide.with(context)
                .load(movie.getPosterUri()) // Giả sử bạn có một URI hình ảnh
                .into(holder.imageView);

        // Sự kiện click vào item để sửa
        holder.itemView.setOnClickListener(v -> showEditMovieDialog(movie));

        // Sự kiện long-click để xóa
        holder.itemView.setOnLongClickListener(v -> {
            showDeleteConfirmationDialog(movie);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    // Hiển thị dialog sửa thông tin phim
    private void showEditMovieDialog(Movie movie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Chỉnh sửa thông tin phim")
                .setItems(new CharSequence[]{"Sửa Tiêu Đề", "Sửa Thể Loại", "Sửa Đánh Giá", "Sửa Mô Tả"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            // Chỉnh sửa tiêu đề
                            break;
                        case 1:
                            // Chỉnh sửa thể loại
                            break;
                        case 2:
                            // Chỉnh sửa đánh giá
                            break;
                        case 3:
                            // Chỉnh sửa mô tả
                            break;
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    // Hiển thị dialog xác nhận xóa phim
    private void showDeleteConfirmationDialog(Movie movie) {
        new AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa bộ phim này không?")
                .setPositiveButton("Xóa", (dialog, which) -> deleteMovie(movie))
                .setNegativeButton("Hủy", null)
                .show();
    }

    // Xóa phim khỏi Firestore
    private void deleteMovie(Movie movie) {
        db.collection("movies")
                .document(movie.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Phim đã được xóa", Toast.LENGTH_SHORT).show();
                    movieList.remove(movie);
                    notifyDataSetChanged(); // Cập nhật lại RecyclerView
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Lỗi khi xóa phim: " + e.getMessage(), Toast.LENGTH_SHORT).show());
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
