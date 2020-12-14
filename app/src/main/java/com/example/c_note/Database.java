package com.example.c_note;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class Database  extends SQLiteOpenHelper  { // alt + enter
    public Database( Context context,  String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // truy vấn không trả về  : CREATE , INSERT , UPDATE , DELETE
    public  void QueryData(String sql){ // truy vấn
        SQLiteDatabase database = getWritableDatabase(); // ghi du lieu
        database.execSQL(sql);
    }
    // trả về : SELECT
    public Cursor GetData(String sql){ // lấy dữ liệu trả về dạng con trỏ
       SQLiteDatabase database = getReadableDatabase();
       return database.rawQuery(sql , null);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
