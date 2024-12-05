package fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<User> userList;

    public UserAdapter(Context context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nguoidung, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position); // Lấy người dùng từ danh sách
        Log.d("UserAdapter", "Binding user: " + user.getEmail() + ", Gói: " + user.getGoidachon() + ", Thời gian thanh toán: " + user.getTimePaid());
        // Gán giá trị cho các TextView
        holder.tvEmail.setText(user.getEmail() != null ? user.getEmail() : "N/A");
        holder.tvGoiDachon.setText(user.getGoidachon() != null ? user.getGoidachon() : "N/A");

        holder.tvTimePaid.setText(user.getTimePaid() != null ? "Thanh toán vào: " + user.getTimePaid() : "N/A");
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmail, tvGoiDachon,  tvTimePaid;

        public UserViewHolder(View itemView) {
            super(itemView);

            tvEmail = itemView.findViewById(R.id.emailnguoidung);
            tvGoiDachon = itemView.findViewById(R.id.goidachon);

            tvTimePaid = itemView.findViewById(R.id.timejoin);
        }
    }
}
