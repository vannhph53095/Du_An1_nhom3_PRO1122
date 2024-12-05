package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class Manhinhthanhtoan extends DialogFragment {

    private TextView tvSelectedService, tvTotalPrice;
    private Button btnPay;
    private String selectedService = "";
    private int totalPrice = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manhinhthanhtoan, container, false);

        tvSelectedService = view.findViewById(R.id.tv_selected_service);
        tvTotalPrice = view.findViewById(R.id.tv_total_price);
        btnPay = view.findViewById(R.id.btn_pay);

        // Xử lý chọn gói dịch vụ
        view.findViewById(R.id.layout_normal).setOnClickListener(v -> updateService(view, "Gói Thường - 480p", 30000));
        view.findViewById(R.id.layout_premium).setOnClickListener(v -> updateService(view, "Gói Premium - 1080p", 70000));

        // Xử lý thanh toán
        btnPay.setOnClickListener(v -> processPayment());

        return view;
    }

    private void updateService(View rootView, String serviceName, int price) {
        selectedService = serviceName;
        totalPrice = price;

        tvSelectedService.setText("Dịch vụ đã chọn: " + serviceName);
        tvTotalPrice.setText("Tổng tiền: " + price + " VNĐ");

        // Đặt màu cho từng layout
        View layoutNormal = rootView.findViewById(R.id.layout_normal);
        View layoutPremium = rootView.findViewById(R.id.layout_premium);

        // Đổi màu layout được chọn
        int selectedColor = getResources().getColor(R.color.selected_color);
        int defaultColor = getResources().getColor(R.color.default_color);

        layoutNormal.setBackgroundColor(serviceName.contains("Thường") ? selectedColor : defaultColor);
        layoutPremium.setBackgroundColor(serviceName.contains("Premium") ? selectedColor : defaultColor);
    }

    private void processPayment() {
        if (selectedService.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng chọn gói dịch vụ!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mô phỏng thanh toán ảo
        simulateMomoPayment();
    }

    private void simulateMomoPayment() {
        Toast.makeText(getContext(), "Đang xử lý thanh toán MoMo...", Toast.LENGTH_SHORT).show();

        boolean isPaymentSuccessful = Math.random() < 0.8; // Xác suất thành công 80%

        if (isPaymentSuccessful) {
            savePaymentDetails();
            Toast.makeText(getContext(), "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
            dismiss(); // Đóng dialog
        } else {
            Toast.makeText(getContext(), "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePaymentDetails() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "Bạn cần đăng nhập để lưu thanh toán!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = auth.getCurrentUser().getUid();

        Map<String, Object> user = new HashMap<>();
        user.put("email", auth.getCurrentUser().getEmail());
        user.put("hasPaid", true);
        user.put("timePaid", new Timestamp(new Date()));
        user.put("goidachon", selectedService);
        user.put("sotien", totalPrice);

        db.collection("users").document(uid)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Thông tin thanh toán đã được lưu!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Lưu thông tin thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                });
    }
}
