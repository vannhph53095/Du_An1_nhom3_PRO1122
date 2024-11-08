package fpoly.ph53095.nhom3_du_an_1_pro1122;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.PhimMoi;

public class List_phimmoi_Adapter extends RecyclerView.Adapter<List_phimmoi_Adapter.ViewHolder> {

    private List<PhimMoi> phimList; // Giả sử bạn có một lớp Phim

    public List_phimmoi_Adapter(List<PhimMoi> phimList) {
        this.phimList = phimList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_trangchu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhimMoi phimMoi = phimList.get(position);
        holder.titlephimmoi.setText(phimMoi.getTitle()); // Giả sử bạn có phương thức getTitle()
        holder.imgphimmoi.setImageResource(phimMoi.getImage()); // Giả sử bạn có phương thức getImageResId()
    }

    @Override
    public int getItemCount() {
        return phimList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titlephimmoi;
        ImageView imgphimmoi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titlephimmoi = itemView.findViewById(R.id.titlephimmoi);
            imgphimmoi = itemView.findViewById(R.id.imgphimmoi);
        }
    }

}
