package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView uploadNotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        setOnClicks();
    }

    private void setView() {
        uploadNotice=findViewById(R.id.UploadNotice);

    }
    private void setOnClicks(){
        uploadNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.UploadNotice:
                Intent intent=new Intent(MainActivity.this,UploadNotice.class);
                startActivity(intent);
                break;

        }

    }
}