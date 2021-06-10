package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adminapp.UploadClasses.UploadGalleryImage;
import com.example.adminapp.UploadClasses.UploadNotice;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView uploadNotice,uploadGalleryImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        setOnClicks();
    }

    private void setView() {
        uploadNotice=findViewById(R.id.UploadNoticeMain);
        uploadGalleryImage=findViewById(R.id.UploadGalleryImageMain);

    }
    private void setOnClicks(){

        uploadNotice.setOnClickListener(this);
        uploadGalleryImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.UploadNoticeMain:
                Intent intent=new Intent(MainActivity.this, UploadNotice.class);
                startActivity(intent);
                break;
            case R.id.UploadGalleryImageMain:
                startActivity(new Intent(this, UploadGalleryImage.class));
                break;

        }

    }
}