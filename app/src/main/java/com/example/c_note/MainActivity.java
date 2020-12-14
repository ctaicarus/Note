package com.example.c_note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;
    Button btnwu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnwu = findViewById(R.id.btn_baothuc);
        lvCongViec = findViewById(R.id.listView);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this , R.layout.dong_cong_viec , arrayCongViec);
        lvCongViec.setAdapter(adapter);
        // tao databate
        database = new Database(this , "note.sqlite" , null , 1);
        // tạo bảng
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT , TenCV VARCHAR(200))");

        // inserst data


        GetDataCongViec();

        btnwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcv = new Intent(MainActivity.this , MainActivityWakeUp.class);
                startActivity(intentcv);
            }
        });
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.memuAdd){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }
    private void GetDataCongViec(){
        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec") ;
        arrayCongViec.clear();
        while(dataCongViec.moveToNext()){
            String ten = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id , ten));

        }
        adapter.notifyDataSetChanged();

    }
    public void DialogXoaCV(String tencv , final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("bạn chắc chứ ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '"+ id +"' ");
                GetDataCongViec();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
    public void DialogSuaCongViec(String ten , final int id){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua);
        final EditText edtTenCv = dialog.findViewById(R.id.edittextTenCvEdit);
        edtTenCv.setText(ten);
        Button btnXacNhan = dialog.findViewById(R.id.button_them_edit);
        Button btnHuy = dialog.findViewById(R.id.button_huy_edit);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edtTenCv.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '"+ tenMoi + "' WHERE Id = '" +  id + "' ");
                Toast.makeText(MainActivity.this , "Đã cập nhập", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataCongViec();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void DialogThem(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_cong_viec);
        dialog.show();
        final EditText editText = dialog.findViewById(R.id.editTextTenCv);
        Button btnThem = dialog.findViewById(R.id.buttonThem);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tencv = editText.getText().toString();
                if(tencv.equals("")){
                    Toast.makeText(MainActivity.this , " Nhập tên công việc" , Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("INSERT INTO CongViec VALUES(null, '"+ tencv +"')");
                    Toast.makeText(MainActivity.this , "Đã thêm " ,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();
                }
            }
        });
    }
}
