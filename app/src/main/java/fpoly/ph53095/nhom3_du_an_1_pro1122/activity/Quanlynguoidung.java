package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;
import fpoly.ph53095.nhom3_du_an_1_pro1122.Adapter.UserAdapter;
import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.User;

public class Quanlynguoidung extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlynguoidung);

        recyclerView = findViewById(R.id.recyclerViewNguoiDung);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        userAdapter = new UserAdapter(this, userList);
        recyclerView.setAdapter(userAdapter);

        loadUsersFromFirestore();
    }


    private void loadUsersFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userList.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String email = document.getString("email");
                            String goidachon = document.getString("goidachon");
                            Timestamp timePaidTimestamp = document.getTimestamp("timePaid");

                            String formattedDate = "";
                            if (timePaidTimestamp != null) {
                                formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                                        .format(timePaidTimestamp.toDate());
                            }


                            userList.add(new User(email, goidachon, formattedDate));
                        }
                        userAdapter.notifyDataSetChanged(); // Cập nhật giao diện
                    } else {
                        Log.e("FirestoreError", "Error getting documents: ", task.getException());
                    }
                });
    }
}
