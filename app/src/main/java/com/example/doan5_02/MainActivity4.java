package com.example.doan5_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    // Hiên thị danh sách nhân viên

    ListView lsView;
    TextView thongBao, thongBao1;
    ArrayList<String> arrList;
    ArrayAdapter<String> adapter;
    //    String DB_NAME = "DuLieuNguoiDung.db";
//    private String DB_PATH = "/databases";
    String DATABASE_NAME = "DuLieuNguoiDung.db";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        addControls();
        xyLySaoChep();
        showAllContactOnListView();

    }



    private void addControls() {
        lsView = findViewById(R.id.lsView);
        //thongBao1.setText("Rồi sẽ ok thôi");
        if (lsView != null) {
            arrList = new ArrayList<>();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrList);
            lsView.setAdapter(adapter);
        } else {
            // Xử lý trường hợp lsView không được tìm thấy.
            Log.e("MainActivity", "ListView (lsView) is null");
        }
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
                thongBao.setText("Sao chép dữ liệu thành công");
                //Toast.makeText(this, "Sao chep du lieu thanh cong", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                thongBao.setText("Sao chép dữ liệu thất bại");
            }
        }
    }

    // Hiển thị toàn bộ dữ liệu trong file
    private void showAllContactOnListView() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.query("Person", null, null, null, null, null,  null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            arrList.add("ID Nhân viên: "  + id + "- Tên nhân viên: " + name);

        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }


}