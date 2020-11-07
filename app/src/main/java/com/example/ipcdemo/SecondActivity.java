package com.example.ipcdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d("SecondActivity", "UserManager.userId:" + UserManager.userId);
    }

    public void startThrid(View view) {
        Intent intent=new Intent(SecondActivity.this,ThirdActivity.class);
        startActivity(intent);
    }
}
