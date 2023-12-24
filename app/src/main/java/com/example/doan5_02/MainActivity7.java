package com.example.doan5_02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity7 extends AppCompatActivity {

    String DATABASE_NAME = "DuLieuNguoiDung.db";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;
    EditText etNhapIDGoc, etTenMoi;
    TextView textName;
    ArrayList<String> arrList;
    ArrayAdapter<String> adapter;
    Button btnXoa, btnSua, btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        addControls();
        xyLySaoChep();
        showAllContactOnListView();
        eventBtn();
    }

    private void eventBtn() {
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaId();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suaName();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity7.this, MainActivity3.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        etNhapIDGoc = findViewById(R.id.idGoc);
        etTenMoi = findViewById(R.id.tenMoi);
        textName = findViewById(R.id.tenGoc);
        btnXoa = findViewById(R.id.buttonXoaIDNV);
        btnSua = findViewById(R.id.buttonSuaNV);
        btn = findViewById(R.id.button4);
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
        Cursor cursor = database.query("Person", null, null, null, null, null,  null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            arrList.add(id + "," +  name);
        }
        cursor.close();
//        adapter.notifyDataSetChanged();
    }

    public void xoaId() {
        String id = etNhapIDGoc.getText().toString();
        int intId = Integer.valueOf(id);
        String namegoc = null;
        int positionToDelete = -1;

        for (int i = 0; i < arrList.size(); i++) {
            String[] parts = arrList.get(i).split(",");
            int idgoc = Integer.parseInt(parts[0]);
            namegoc = parts[1];
            if (idgoc == intId) {
                positionToDelete = i;
                break;
            }
        }

        // Kiểm tra giá trị positionToDelete
        if (positionToDelete != -1) {
            // Hiển thị tên trước khi xóa (có thể không cần thiết)
            textName.setText(namegoc);

            // Hiển thị hộp thoại xác nhận xóa
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete " + id + " ?");
            builder.setMessage("Bạn có muốn xóa " + namegoc + " ?");
            int finalPositionToDelete = positionToDelete;

            String finalNamegoc = namegoc;
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        database.delete("Person", "ID=?", new String[]{String.valueOf(intId)});
                        textName.setText("Xóa thành công");
                    } catch (SQLiteException e) {
                        e.printStackTrace();
                        // Xử lý lỗi xóa từ cơ sở dữ liệu
                    }
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Bạn có thể thêm các hành động khi người dùng chọn "No"
                    textName.setText("");
                }
            });

            builder.create().show();
        } else {
            // Hiển thị thông báo nếu không tìm thấy ID
            Toast.makeText(this, "Không tìm thấy ID " + id, Toast.LENGTH_SHORT).show();
        }

    }

    public void suaName(){
        String id = etNhapIDGoc.getText().toString();
        int intId = Integer.valueOf(id);

        String namegoc = null;
        int positionToDelete = -1;
        for (int i = 0; i < arrList.size(); i++) {
            String[] parts = arrList.get(i).split(",");
            int idgoc = Integer.parseInt(parts[0]);
            namegoc = parts[1];
            if (idgoc == intId) {
                positionToDelete = i;
                break;
            }
        }

        String nameNew = etTenMoi.getText().toString();
        ContentValues values = new ContentValues();
        values.put("ID", intId);
        values.put("Name", nameNew);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + id + " ?");
        builder.setMessage("Bạn có muốn xóa " + namegoc + " ?");
        int finalPositionToDelete = positionToDelete;

        String finalNamegoc = namegoc;
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.update("Person", values, "ID=?", new String[]{String.valueOf(intId)});
                textName.setText("Sửa thành công");
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Bạn có thể thêm các hành động khi người dùng chọn "No"
            }
        });

        builder.create().show();





    }

}