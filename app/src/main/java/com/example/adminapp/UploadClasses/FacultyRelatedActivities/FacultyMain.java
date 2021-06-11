package com.example.adminapp.UploadClasses.FacultyRelatedActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Toast;

import com.example.adminapp.Adapters.FacultyAdapter;
import com.example.adminapp.HandleClickInterface.OnItemClickListener;
import com.example.adminapp.Models.Faculty;
import com.example.adminapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FacultyMain extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {
    private FloatingActionButton floatingActionButton;
    private List<Faculty> directorList, csList, itList, eeList, eeeList, ceList, meList;
    private View directorNoData, csNoData, itNoData, eeNoData, eeeNoData, meNoData, ceNoData;
    private RecyclerView directorRecyclerView, csRecyclerView, itRecyclerView, eeRecyclerView, eeeRecyclerView, meRecyclerView, ceRecyclerView;
    private FacultyAdapter facultyAdapter;
    private DatabaseReference databaseReference, tempRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_main);
        setView();
        setAdapters();
        setListeners();
        setDirector();
        setCSE();
        setIT();
        setEE();
        setEEE();
        setME();
        setCE();
    }

    private void setCE() {
        ceRecyclerView.setHasFixedSize(true);
        ceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tempRef=databaseReference.child("Civil Engineering");
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               ceList=new ArrayList<>();
               if(!snapshot.exists()){
                   ceNoData.setVisibility(View.VISIBLE);
                   ceRecyclerView.setVisibility(View.GONE);
               }
               else{
                   for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                       ceList.add(dataSnapshot.getValue(Faculty.class));
                   }
                   facultyAdapter=new FacultyAdapter(FacultyMain.this,ceList,FacultyMain.this);
                   ceRecyclerView.setAdapter(facultyAdapter);
                   ceRecyclerView.setVisibility(View.VISIBLE);
                   ceNoData.setVisibility(View.GONE);
               }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(FacultyMain.this,"Something went wrong, try again!",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setME() {

    }

    private void setEEE() {

    }

    private void setEE() {

    }

    private void setIT() {

    }

    private void setCSE() {

    }

    private void setDirector() {

    }

    private void setAdapters() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = databaseReference.child("Faculty");

    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(this);
    }

    private void setView() {
        floatingActionButton = findViewById(R.id.floatingActionButton);

        //No Data Layout setView
        directorNoData = findViewById(R.id.directorNoData);
        csNoData = findViewById(R.id.CSENoData);
        itNoData = findViewById(R.id.ITNoData);
        eeeNoData = findViewById(R.id.EEENoData);
        eeNoData = findViewById(R.id.EENoData);
        ceNoData = findViewById(R.id.CENoData);
        meNoData = findViewById(R.id.MENoData);

        //setView recycler views
        directorRecyclerView = findViewById(R.id.directorRecyclerView);
        csRecyclerView = findViewById(R.id.CSERecyclerView);
        itRecyclerView = findViewById(R.id.ITRecyclerView);
        eeRecyclerView = findViewById(R.id.EERecyclerView);
        eeeRecyclerView = findViewById(R.id.EEERecyclerView);
        ceRecyclerView = findViewById(R.id.CERecyclerView);
        meRecyclerView = findViewById(R.id.MERecyclerView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
                openAddFaculty();
                break;
        }

    }

    private void openAddFaculty() {
        startActivity(new Intent(FacultyMain.this, AddFaculty.class));
    }

    @Override
    public void onClick(int pos) {


    }
}