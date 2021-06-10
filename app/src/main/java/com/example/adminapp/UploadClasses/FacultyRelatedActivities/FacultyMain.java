package com.example.adminapp.UploadClasses.FacultyRelatedActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adminapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FacultyMain extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_main);
        setView();
        setListeners();
    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(this);
    }

    private void setView() {
        floatingActionButton=findViewById(R.id.floatingActionButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingActionButton:
                openAddFaculty();
                break;
        }

    }

    private void openAddFaculty() {
        startActivity(new Intent(FacultyMain.this,AddFaculty.class));
    }
}