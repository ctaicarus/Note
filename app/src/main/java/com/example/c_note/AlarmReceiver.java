package com.example.c_note;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Toi trong receiver","xin chao");
        String s = intent.getExtras().getString("extra");
        Intent myIntend = new Intent(context, Music.class);
        myIntend.putExtra("extra",s);
        context.startService(myIntend);
    }
}
