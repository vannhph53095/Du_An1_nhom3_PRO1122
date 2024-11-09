package fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.PhimMoi;

public class List_phimmoi_Adapter extends RecyclerView.Adapter<List_phimmoi_Adapter.ViewHolder> {

    private List<PhimMoi> phimList;
    private boolean[] isHearted;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "PhimPreferences";

    public List_phimmoi_Adapter(Context context, List<PhimMoi> phimList) {
        this.phimList = phimList;
        this.isHearted = new boolean[phimList.size()];
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);


        for (int i = 0; i < phimList.size(); i++) {
            isHearted[i] = sharedPreferences.getBoolean("heart_" + i, false);
        }
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
        holder.titlephimmoi.setText(phimMoi.getTitle());
        holder.imgphimmoi.setImageResource(phimMoi.getImage());


        holder.yeuthichbutton.setImageResource(
                isHearted[position] ? R.drawable.heart_icon2 : R.drawable.heart_ic
        );


        holder.yeuthichbutton.setOnClickListener(v -> {
            isHearted[position] = !isHearted[position];
            holder.yeuthichbutton.setImageResource(
                    isHearted[position] ? R.drawable.heart_icon2 : R.drawable.heart_ic
            );


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("heart_" + position, isHearted[position]);
            editor.apply();
        });
    }

    @Override
    public int getItemCount() {
        return phimList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titlephimmoi;
        ImageView imgphimmoi;
        ImageView yeuthichbutton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titlephimmoi = itemView.findViewById(R.id.titlephimmoi);
            imgphimmoi = itemView.findViewById(R.id.imgphimmoi);
            yeuthichbutton = itemView.findViewById(R.id.yeuthichbutton);
        }
    }
}
