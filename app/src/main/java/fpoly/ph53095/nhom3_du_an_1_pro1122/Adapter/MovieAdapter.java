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

    // Phương thức tạo view holder cho mỗi item trong RecyclerView
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    // Phương thức gắn dữ liệu vào các view trong mỗi item
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        // Gán tiêu đề phim vào TextView
        holder.txtMovieTitle.setText(movie.getTitle());

        // Load hình ảnh bằng Glide từ URI của phim
        Glide.with(context)
                .load(movie.getPosterUri())
                .placeholder(R.drawable.ic_launcher_background) // Hình ảnh mặc định khi đang tải
                .into(holder.imageView);

        // Kiểm tra trạng thái yêu thích của phim và thay đổi icon tương ứng
        if (movie.isLiked()) {
            holder.yeuthichbutton.setImageResource(R.drawable.heart_icon2); // Icon yêu thích
        } else {
            holder.yeuthichbutton.setImageResource(R.drawable.heart_ic); // Icon chưa yêu thích
        }

        // Xử lý sự kiện click vào view yêu thích
        holder.viewyeuthich.setOnClickListener(v -> {
            boolean isCurrentlyLiked = movie.isLiked();
            movie.setLiked(!isCurrentlyLiked);
            notifyItemChanged(position); // Cập nhật lại item sau khi thay đổi trạng thái

            // Cập nhật trạng thái yêu thích trên Firestore
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
            return true;  // Trả về true để chỉ ra rằng sự kiện đã được xử lý
        });
    }

    // Trả về số lượng item trong danh sách
    @Override
    public int getItemCount() {
        return movieList.size();
    }

    // Lớp ViewHolder để chứa các view của mỗi item
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView txtMovieTitle;
        public LinearLayout viewyeuthich;
        public ImageView imageView, yeuthichbutton;

        // Constructor gán các view vào các biến
        public MovieViewHolder(View itemView) {
            super(itemView);
            txtMovieTitle = itemView.findViewById(R.id.txtMovieTitle);
            viewyeuthich = itemView.findViewById(R.id.viewyeuthich);
            imageView = itemView.findViewById(R.id.imageView);
            yeuthichbutton = itemView.findViewById(R.id.yeuthichbutton);
        }
    }

    // Interface cho các sự kiện click của người dùng
    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);           // Xử lý khi click vào item
        void onMovieLongClick(Movie movie);       // Xử lý khi long click vào item
    }
}
