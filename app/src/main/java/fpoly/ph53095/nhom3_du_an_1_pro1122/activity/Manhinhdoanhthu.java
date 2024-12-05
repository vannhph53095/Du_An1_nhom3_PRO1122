package fpoly.ph53095.nhom3_du_an_1_pro1122.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

import fpoly.ph53095.nhom3_du_an_1_pro1122.R;

public class Manhinhdoanhthu extends AppCompatActivity {
    private FirebaseFirestore db;
    private TextView txtTongDoanhThu;
    private EditText edtNgayBatDau, edtNgayKetThuc;
    private Button btnTinhDoanhThu, btnTuNgay, btnDenNgay;
    private String ngayBatDau, ngayKetThuc;

    private static final String TAG = "Manhinhdoanhthu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhdoanhthu);


        db = FirebaseFirestore.getInstance();
        txtTongDoanhThu = findViewById(R.id.txtTongDoanhThu);
        edtNgayBatDau = findViewById(R.id.edtNgayBatDau);
        edtNgayKetThuc = findViewById(R.id.edtNgayKetThuc);
        btnTinhDoanhThu = findViewById(R.id.btnTinhDoanhThu);
        btnTuNgay = findViewById(R.id.btnTuNgay);
        btnDenNgay = findViewById(R.id.btnDenNgay);

        btnTuNgay.setOnClickListener(v -> chonNgayBatDau());
        btnDenNgay.setOnClickListener(v -> chonNgayKetThuc());
        btnTinhDoanhThu.setOnClickListener(v -> {
            if (validateNgay()) {
                Log.d(TAG, "Bắt đầu tính doanh thu.");
                getDoanhThuFromFirestore();
            }
        });
    }

    private boolean validateNgay() {
        if (ngayBatDau == null && ngayKetThuc == null) {
            Log.d(TAG, "Không chọn ngày, tính tổng doanh thu cho tất cả giao dịch.");
            return true;
        }

        if (ngayBatDau == null || ngayKetThuc == null) {
            Log.e(TAG, "Chưa chọn đầy đủ ngày: ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc);
            Toast.makeText(this, "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc", Toast.LENGTH_SHORT).show();
            return false;
        }

        Log.d(TAG, "Ngày hợp lệ: ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc);
        return true;
    }

    private void chonNgayBatDau() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            ngayBatDau = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
            edtNgayBatDau.setText(ngayBatDau);
            Log.d(TAG, "Chọn ngày bắt đầu: " + ngayBatDau);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void chonNgayKetThuc() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            ngayKetThuc = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
            edtNgayKetThuc.setText(ngayKetThuc);
            Log.d(TAG, "Chọn ngày kết thúc: " + ngayKetThuc);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void getDoanhThuFromFirestore() {
        db.collection("users")
                .whereEqualTo("hasPaid", true)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalRevenue = 0;

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        Date startDate = null;
                        Date endDate = null;

                        boolean isFilterByDate = (ngayBatDau != null && ngayKetThuc != null); // Kiểm tra xem có lọc theo ngày không

                        if (isFilterByDate) {
                            try {
                                startDate = sdf.parse(ngayBatDau);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(sdf.parse(ngayKetThuc));
                                calendar.set(Calendar.HOUR_OF_DAY, 23);
                                calendar.set(Calendar.MINUTE, 59);
                                calendar.set(Calendar.SECOND, 59);
                                endDate = calendar.getTime();

                                Log.d(TAG, "Ngày bắt đầu: " + startDate + ", Ngày kết thúc (cuối ngày): " + endDate);
                            } catch (Exception e) {
                                Log.e(TAG, "Lỗi chuyển đổi ngày: " + e.getMessage());
                                Toast.makeText(this, "Lỗi khi xử lý ngày", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            try {
                                long sotien = document.getLong("sotien");
                                com.google.firebase.Timestamp timePaidTimestamp = document.getTimestamp("timePaid");
                                Date datePaid = timePaidTimestamp != null ? timePaidTimestamp.toDate() : null;

                                Log.d(TAG, "Số tiền: " + sotien + ", Ngày thanh toán: " + datePaid);

                                if (!isFilterByDate || (datePaid != null && isWithinTimeRange(datePaid, startDate, endDate))) {
                                    totalRevenue += sotien;
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "Lỗi khi xử lý dữ liệu document: " + e.getMessage());
                            }
                        }

                        Log.d(TAG, "Tổng doanh thu: " + totalRevenue);
                        txtTongDoanhThu.setText(String.format(Locale.getDefault(), "%,.0f", totalRevenue));
                    } else {
                        Log.e(TAG, "Lỗi khi lấy dữ liệu từ Firestore: " + task.getException());
                        Toast.makeText(this, "Lỗi khi lấy dữ liệu từ Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    private boolean isWithinTimeRange (Date datePaid, Date startDate, Date endDate){
                boolean result = !datePaid.before(startDate) && !datePaid.after(endDate);
                Log.d(TAG, "Kiểm tra ngày: datePaid=" + datePaid + ", result=" + result);
                return result;
            }
        }

