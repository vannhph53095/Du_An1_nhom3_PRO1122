package fpoly.ph53095.nhom3_du_an_1_pro1122;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView titlephimmoi;
    ImageView imgphimmoi;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        titlephimmoi = itemView.findViewById(R.id.titlephimmoi);
        imgphimmoi = itemView.findViewById(R.id.imgphimmoi);
    }
}
