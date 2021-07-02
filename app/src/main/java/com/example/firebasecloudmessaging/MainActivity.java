package com.example.firebasecloudmessaging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView token;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token = findViewById(R.id.token);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                token.setText(SharedPrefManager.getInstance(MainActivity.this).getToken());
            }
        };

        if (SharedPrefManager.getInstance(MainActivity.this).getToken() != null){
            token.setText(SharedPrefManager.getInstance(MainActivity.this).getToken());
        }
        System.out.println("----------------------------------------------------");
        System.out.println("----------------------------------------------------");
        System.out.println("Token: " + SharedPrefManager.getInstance(MainActivity.this).getToken());
        System.out.println("----------------------------------------------------");
        System.out.println("----------------------------------------------------");

        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseMessagingService.TOKEN_BROADCAST));
    }
}