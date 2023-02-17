package com.example.loginnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class firstpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
    }

    public void onclickQR(View view) {
        Intent intent = new Intent(firstpage.this, QRloginNew.class);
        startActivity(intent);

    }
}