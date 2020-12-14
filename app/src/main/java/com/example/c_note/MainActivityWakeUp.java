package com.example.c_note;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivityWakeUp extends AppCompatActivity {
    Button btnHenGio, btnDungLai;
    TextView txtHienthi;
    TimePicker timePicker;
    Calendar calandar = Calendar.getInstance();
    AlarmManager alarmManager ; // truy cập vào hệ thống báo động của điện thoại
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wake_up);
        anhxa();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Intent intent = new Intent(MainActivityWakeUp.this, AlarmReceiver.class);
        btnHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calandar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calandar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                intent.putExtra("extra","on");
                pendingIntent = PendingIntent.getBroadcast(
                        MainActivityWakeUp.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); // tồn tại ngay cả khi thoát ứng dụng
                alarmManager.set(
                        AlarmManager.RTC_WAKEUP, calandar.getTimeInMillis(), pendingIntent);
                txtHienthi.setText("Bạn đã hẹn giờ: " + gio + "h: " + phut);
            }
        });
        btnDungLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtHienthi.setText("Dừng lại");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra","off");
            }
        });
    }
    public void anhxa(){
        btnHenGio = findViewById(R.id.btndatgio);
        btnDungLai = findViewById(R.id.btndung);
        txtHienthi = findViewById(R.id.txthienthi);
        timePicker = findViewById(R.id.timePicker);
    }
}
