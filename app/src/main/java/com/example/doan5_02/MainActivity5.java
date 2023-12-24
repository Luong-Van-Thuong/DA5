package com.example.doan5_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity5 extends AppCompatActivity {


    EditText etId, etThang, etNam, etLuong;
    Button btnTinhLuong, btn;
    TextView soNgayLam, soGioMuon, soGioTangCa;
//    ListView listViewDS;
//    ArrayAdapter<String> adapter;
    ArrayList<String> arrList;
    String DATABASE_NAME = "test01.db";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;
    List<ChamCongNhanVien> lccnv;
    ChamCongNhanVien ccnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        addContronls();
        xyLySaoChep();
        showAllContactOnListView();
        btnTinhLuong();
        btnHome();
    }

    private void btnHome() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity5.this, MainActivity3.class);
                startActivity(intent);
            }
        });

    }

    private void btnTinhLuong() {
        btnTinhLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float luongCoBan = 4500000f;
                float phuCapXangXe = 500000f;
                float phuCapDienThoai = 200000f;
                float tienAnGiuaCa = 800000f;
                int soNgayDiLam = ngayCong();
                float  gioTangCa = (float) tinhGio();
                float luong1Ngay = (luongCoBan + phuCapDienThoai + phuCapXangXe + tienAnGiuaCa) / 26.0f;
                float luongTangCa = luong1Ngay/9*gioTangCa;

                // Thêm kiểu dữ liệu dấu phẩy động cho biến Luong
                float Luong = soNgayDiLam * luong1Ngay + luongTangCa;
                String TextLuong = String.valueOf(Luong);
                etLuong.setText("Số tiền nhận được là: " + TextLuong);

            }
        });
    }

    private void addContronls() {
        etId = findViewById(R.id.nhapId);
        etThang = findViewById(R.id.nhapThang);
        etNam = findViewById(R.id.nhapNam);
        soNgayLam = findViewById(R.id.soNgayDiLam);
        soGioTangCa = findViewById(R.id.gioTangCa);
        btnTinhLuong = findViewById(R.id.btnTinhLuong);
        etLuong = findViewById(R.id.etLuong);
        arrList = new ArrayList<>();
        soGioMuon = findViewById(R.id.textView14);
        btn = findViewById(R.id.button2);
//        listViewDS = findViewById(R.id.lsDS);
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrList);
//        listViewDS.setAdapter(adapter);
    }

    private String layDuongDanLuuTru(){
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }
    //B2 Sao chep du lieu vao file khac
    private void CopyDatabaseFromAsset() {
        try{
            InputStream myInput = getAssets().open(DATABASE_NAME);
            //Lay duong  dan luu tru du lieu
            String outFileName = layDuongDanLuuTru();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            //Kiem tra xem file toi tai hay chua
            if(!f.exists()){
                f.mkdir();
            }
            //Sao chep du lieu
            OutputStream myOutpuut = new FileOutputStream(outFileName);
            //
            byte[] buffer = new byte[1024];
            int length;
            while((length = myInput.read(buffer)) > 0){
                myOutpuut.write(buffer, 0, length);
            }
            //Dong cac file
            myOutpuut.flush();
            myOutpuut.close();
            myInput.close();


        }catch (Exception ex){
            Log.e("Loi_SaoChep", ex.toString());

        }
    }
    //B3
    private void xyLySaoChep() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try {
                CopyDatabaseFromAsset();
                Toast.makeText(this, "Sao chep du lieu thanh cong", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    // Hiển thị toàn bộ dữ liệu trong file
    private void showAllContactOnListView() {
        lccnv = new ArrayList<>();
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.query("test", null, null, null, null, null,  null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String thoiGian = cursor.getString(2);
            String ngayThangNam = cursor.getString(3);
            ccnv = new ChamCongNhanVien(id, name, thoiGian, ngayThangNam);
            lccnv.add(ccnv);
            arrList.add(id + "," + name + "," + thoiGian + "," + ngayThangNam);
//            Toast.makeText(this, lccnv.toString(), Toast.LENGTH_LONG).show();
        }
        cursor.close();
//        adapter.notifyDataSetChanged();
    }

    public int ngayCong(){
        String userId = etId.getText().toString();
        String month = etThang.getText().toString();
        String year = etNam.getText().toString();
//// Chuyển đổi userId, month, year thành kiểu int nếu cần thiết
        int id = Integer.parseInt(userId);
        int inputMonth = Integer.parseInt(month);
        int inputYear = Integer.parseInt(year);

// Lưu trữ ngày khác nhau trong tháng và năm được nhập từ người dùng và có id là id
        Set<String> uniqueDates = new HashSet<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");

        for (String entry : arrList) {
            String[] parts = entry.split(",");
            int entryId = Integer.parseInt(parts[0]);
            int entryMonth = Integer.parseInt(parts[3].split("/")[1]);
            int entryYear = Integer.parseInt(parts[3].split("/")[2]);

            // Kiểm tra xem id, tháng và năm có trùng khớp không
            if (entryId == id && entryMonth == inputMonth && entryYear == inputYear) {
                String dateStr = parts[3];

                try {
                    Date date = dateFormat.parse(dateStr);
                    uniqueDates.add(dateFormat.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        // In số ngày khác nhau
        int soNgayDiLam = uniqueDates.size();
        String textSoNgay = String.valueOf(soNgayDiLam);
        soNgayLam.setText("Tổng số ngày đi làm: " + textSoNgay);
//        System.out.println("Số ngày khác nhau trong tháng 12 năm 2023 với id 1: " + numUniqueDates);
        return soNgayDiLam;
    }

    public double tinhGio(){
        String userId = etId.getText().toString();
        String month = etThang.getText().toString();
        String year = etNam.getText().toString();

        // Chuyển đổi userId, month, year thành kiểu int nếu cần thiết
        int id = Integer.parseInt(userId);
        int inputMonth = Integer.parseInt(month);
        int inputYear = Integer.parseInt(year);

        // Tạo một bản đồ để lưu trữ thời gian theo từng ngày
        Map<String, List<Date>> timesByDate = new HashMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        // Lọc chỉ những ngày thuộc tháng và năm do người dùng nhập
        for (String entry : arrList) {
            String[] parts = entry.split(",");
            int entryUserId = Integer.parseInt(parts[0]);
            int entryMonth = Integer.parseInt(parts[3].split("/")[1]);
            int entryYear = Integer.parseInt(parts[3].split("/")[2]);

            // Kiểm tra xem id, tháng và năm có trùng khớp không
            if (entryUserId == id && entryMonth == inputMonth && entryYear == inputYear) {
                String dateStr = parts[3];
                String timeStr = parts[2];
                try {
                    Date datetimeObj = timeFormat.parse(timeStr);
                    if (!timesByDate.containsKey(dateStr)) {
                        timesByDate.put(dateStr, new ArrayList<>());
                    }
                    timesByDate.get(dateStr).add(datetimeObj);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        double totalDifferenceHours = 0;

//        long totalDifferenceHours = 0;
        // Tính hiệu thời gian (Max - Min) cho mỗi ngày
        for (Map.Entry<String, List<Date>> entry : timesByDate.entrySet()) {
            String date = entry.getKey();
            List<Date> timesOnDate = entry.getValue();
            if (timesOnDate.size() >= 2) {
                // Sắp xếp thời gian để tìm giá trị lớn nhất và nhỏ nhất
                Collections.sort(timesOnDate);
                Date minTime = timesOnDate.get(0);
                Date maxTime = timesOnDate.get(timesOnDate.size() - 1);
                // Tính hiệu thời gian
                long timeDifferenceMillis = maxTime.getTime() - minTime.getTime();
                double timeDifferenceHours = timeDifferenceMillis / (60.0 * 60.0 * 1000.0);
//                long timeDifferenceHours = timeDifferenceMillis / (60 * 60 * 1000);
                System.out.println("Số giờ đi làm trong ngày " + date + ": " + timeDifferenceHours + " giờ");
                totalDifferenceHours += timeDifferenceHours;
            } else {
                System.out.println("Không đủ dữ liệu để tính hiệu thời gian trong ngày " + date);
            }
        }
        Toast.makeText(this, String.valueOf(totalDifferenceHours), Toast.LENGTH_LONG).show();

//        String timeString = String.format("%.1f", totalDifferenceHours);

        int songay = ngayCong();
        double songayfloat = Double.valueOf(songay);
        // Số giờ 1 người 1 ngày phải làm là 9 tiếng
        double sogiotang1 = totalDifferenceHours - (songayfloat*9);
        if(sogiotang1 <= 0){
            sogiotang1 = 0;
        }
//        int soGioTang = (int) (totalDifferenceHours-songay*9);
//        if (soGioTang < 0){
//            soGioTang = 0;
//        }

        String TextTongGio = String.format("%.1f", sogiotang1);
//        String TextTongGio = String.format("%.1f", totalDifferenceHours);
//        String TextTongGio = String.valueOf(soGioTang);
        soGioTangCa.setText("Số giờ làm thêm được là: " + String.valueOf(totalDifferenceHours));
//        soGioMuon.setText("Hello");
        return sogiotang1;
    }

}