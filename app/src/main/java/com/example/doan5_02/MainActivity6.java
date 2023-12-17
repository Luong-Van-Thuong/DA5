package com.example.doan5_02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity6 extends AppCompatActivity {

    String DATABASE_NAME = "test01.db";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;

    ChamCongNhanVien ccnv;
    List<ChamCongNhanVien> lccnv;

    ArrayAdapter<String> adapter;
    ArrayList<String> arrList;
    EditText etIDMuon, etThangMuon, etNamMuon;
    Button btnNgayDiLam, btnNgayDiMuon;
    ListView lsHienThi;
    TextView hienThiTrangThai, songay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        addControls();
        xyLySaoChep();
        showAllContactOnListView();
        suLyNutNhan();

    }

    private void suLyNutNhan() {
        btnNgayDiMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNgayDiMuon();
            }
        });
        btnNgayDiLam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNgayDiLam();
            }
        });
    }


    private void addControls() {
        etIDMuon = findViewById(R.id.editID);
        etThangMuon = findViewById(R.id.editThang);
        etNamMuon = findViewById(R.id.editNam);
        btnNgayDiLam = findViewById(R.id.buttonNgayDilam);
        btnNgayDiMuon = findViewById(R.id.buttonNgayDIMuon);
        lsHienThi = findViewById(R.id.listViewHienThi);
        lccnv = new ArrayList<>();
        hienThiTrangThai = findViewById(R.id.hienThiNgayOrGio);
        songay = findViewById(R.id.textView18);
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrList);
//        lsHienThi.setAdapter(adapter);
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
                //Toast.makeText(this, "Sao chep du lieu thanh cong", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private void showAllContactOnListView() {
        arrList = new ArrayList<>();
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

        }
        cursor.close();
//        adapter.notifyDataSetChanged();


    }

    // So sánh thời gian
    // Phương thức so sánh thời gian
    private int compareTimes(String time1, String time2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date date1 = format.parse(time1);
            Date date2 = format.parse(time2);
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public void displayNgayDiLam(){

        String userId = etIDMuon.getText().toString();
        String month = etThangMuon.getText().toString();
        String year = etNamMuon.getText().toString();

        int id = Integer.parseInt(userId);
        int thang = Integer.parseInt(month);
        int nam = Integer.parseInt(year);

        Set<String> uniqueDates = new HashSet<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");

        for (String entry : arrList) {
            String[] parts = entry.split(",");

            if (parts.length >= 4) {

                int entryId = Integer.parseInt(parts[0]);
                int entryMonth = Integer.parseInt(parts[3].split("/")[1]);
                int entryYear = Integer.parseInt(parts[3].split("/")[2]);

                // Kiểm tra xem id, tháng và năm có trùng khớp không
                if (entryId == id && entryMonth == thang && entryYear == nam) {
                    String dateStr = parts[3];

                    try {
                        Date date = dateFormat.parse(dateStr);
                        uniqueDates.add(dateFormat.format(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // Chuyển kiểu Set<String> thành kiểu hiển thị Text
//        StringBuilder uniqueDatesStringBuilder = new StringBuilder();
//        for (String uniqueDate : uniqueDates) {
//            uniqueDatesStringBuilder.append(uniqueDate).append("\n");
//        }
        hienThiTrangThai.setText("Những ngày đi làm");
        // Convert Set<String> to a List<String>
        List<String> uniqueDatesList = new ArrayList<>(uniqueDates);
        // Create an ArrayAdapter and set it to the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, uniqueDatesList);
        lsHienThi.setAdapter(adapter);
        int soNgayDiLam = uniqueDates.size();
        String textSoNgay = String.valueOf(soNgayDiLam);
        songay.setText("Tổng số ngày đi làm: " + textSoNgay);

    }


    public void displayNgayDiMuon (){
        String userId = etIDMuon.getText().toString();
        String month = etThangMuon.getText().toString();
        String year = etNamMuon.getText().toString();

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
        List<String> mindates = new ArrayList<>();
        // Tính hiệu thời gian (Max - Min) cho mỗi ngày
        for (Map.Entry<String, List<Date>> entry : timesByDate.entrySet()) {
            String date = entry.getKey();
            List<Date> timesOnDate = entry.getValue();

            if (timesOnDate.size() >= 2) {
                // Sắp xếp thời gian để tìm giá trị lớn nhất và nhỏ nhất
                Collections.sort(timesOnDate);
                Date minTime = timesOnDate.get(0);

                // Kiểm tra nếu thời gian nhỏ nhất lớn hơn 7:00
                if (minTime.getHours() > 7 || (minTime.getHours() == 7 && minTime.getMinutes() > 0)) {
                   // System.out.println("Số giờ đi làm trong ngày " + date + ": " + " giờ");
                    mindates.add(date + " - " + minTime.getHours() + ":" + minTime.getMinutes());

                } else {
                   // System.out.println("Ngày " + date + " có thời gian nhỏ nhất không hợp lệ.");
                }
            } else {
                //System.out.println("Không đủ dữ liệu để tính hiệu thời gian trong ngày " + date);
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mindates);
        lsHienThi.setAdapter(adapter);
    }
}