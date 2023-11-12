package com.example.doan5_02;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SqlTaiKhoan extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    public static final String CUSTOMER_TAIKHOAN = "CUSTOMER_TAIKHOAN";
    public static final String CUSTOMER_MATKHAU = "CUSTOMER_MATKHAU";
    private final Context context;

    public SqlTaiKhoan(@Nullable Context context) {
        super(context, "Doan5.db", null, 1);
        this.context = context;
    }


    @Override
    //Khởi tạo bảng
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + CUSTOMER_TABLE + " (" + CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CUSTOMER_TAIKHOAN + " TEXT, " + CUSTOMER_MATKHAU + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Them du lieu vao bang
    public void addTaiKhoan(ObjectTaiKhoan obTaiKhoan){
        SQLiteDatabase sqlTaiKhoan = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_TAIKHOAN, obTaiKhoan.getTenTaiKhoan());
        cv.put(CUSTOMER_MATKHAU, obTaiKhoan.getMatKhau());
        long insert = sqlTaiKhoan.insert(CUSTOMER_TABLE, null, cv);
        if(insert == -1){
            Toast.makeText(context, "Lỗi tạo dữ liệu", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(context, "Tạo dữ liệu thành công", Toast.LENGTH_LONG).show();

        }
    }



}
