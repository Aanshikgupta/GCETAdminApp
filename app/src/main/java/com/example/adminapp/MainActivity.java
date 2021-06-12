package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adminapp.UploadClasses.DeleteNotice;
import com.example.adminapp.UploadClasses.FacultyRelatedActivities.FacultyMain;
import com.example.adminapp.UploadClasses.UploadEbook;
import com.example.adminapp.UploadClasses.UploadGalleryImage;
import com.example.adminapp.UploadClasses.UploadNotice;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView uploadNotice,uploadGalleryImage,addEbook,addFaculty,deleteNotice;
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
        addEbook=findViewById(R.id.addEbook);
        addFaculty=findViewById(R.id.addFaculty);
        deleteNotice=findViewById(R.id.deleteNotice);

    }
    private void setOnClicks(){

        uploadNotice.setOnClickListener(this);
        uploadGalleryImage.setOnClickListener(this);
        addEbook.setOnClickListener(this);
        addFaculty.setOnClickListener(this);
        deleteNotice.setOnClickListener(this);
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
            case R.id.addEbook:
                startActivity(new Intent(this, UploadEbook.class));
                break;
            case R.id.addFaculty:
                startActivity(new Intent(this, FacultyMain.class));
                break;
            case R.id.deleteNotice:
                startActivity(new Intent(this, DeleteNotice.class));
                break;

        }

    }
}