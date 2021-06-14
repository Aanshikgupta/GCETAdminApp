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

public class FacultyMain extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton floatingActionButton;
    private List<Faculty> directorList, csList, eiList,eceList,itList, eeList, eeeList, ceList, meList;
    private View directorNoData,eceNoData,eiNoData, csNoData, itNoData, eeNoData, eeeNoData, meNoData, ceNoData;
    private RecyclerView directorRecyclerView,eceRecyclerView,eiRecyclerView, csRecyclerView, itRecyclerView, eeRecyclerView, eeeRecyclerView, meRecyclerView, ceRecyclerView;
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
        setECE();
        setEI();
        setME();
        setCE();
    }

    private void setCE() {
        ceRecyclerView.setHasFixedSize(true);
        ceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tempRef = databaseReference.child("Civil Engineering");
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ceList = new ArrayList<>();
                if (!snapshot.exists()) {
                    ceNoData.setVisibility(View.VISIBLE);
                    ceRecyclerView.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ceList.add(dataSnapshot.getValue(Faculty.class));
                    }
                    facultyAdapter = new FacultyAdapter(FacultyMain.this, ceList);
                    ceRecyclerView.setAdapter(facultyAdapter);
                    ceRecyclerView.setVisibility(View.VISIBLE);
                    ceNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(FacultyMain.this, "Something went wrong, try again!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setME() {
        meRecyclerView.setHasFixedSize(true);
        meRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tempRef = databaseReference.child("Mechanical Engineering");
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                meList = new ArrayList<>();
                if (!snapshot.exists()) {
                    meNoData.setVisibility(View.VISIBLE);
                    meRecyclerView.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        meList.add(dataSnapshot.getValue(Faculty.class));
                    }
                    facultyAdapter = new FacultyAdapter(FacultyMain.this, meList);
                    meRecyclerView.setAdapter(facultyAdapter);
                    meRecyclerView.setVisibility(View.VISIBLE);
                    meNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(FacultyMain.this, "Something went wrong, try again!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setEEE() {

        eeeRecyclerView.setHasFixedSize(true);
        eeeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tempRef = databaseReference.child("Electrical and Electronics Engineering");
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                eeeList = new ArrayList<>();
                if (!snapshot.exists()) {
                    eeeNoData.setVisibility(View.VISIBLE);
                    eeeRecyclerView.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        eeeList.add(dataSnapshot.getValue(Faculty.class));
                    }
                    facultyAdapter = new FacultyAdapter(FacultyMain.this, eeeList);
                    eeeRecyclerView.setAdapter(facultyAdapter);
                    eeeRecyclerView.setVisibility(View.VISIBLE);
                    eeeNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(FacultyMain.this, "Something went wrong, try again!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setECE() {

        eceRecyclerView.setHasFixedSize(true);
        eceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tempRef = databaseReference.child("Electrical and Electronics Engineering");
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                eceList = new ArrayList<>();
                if (!snapshot.exists()) {
                    eceNoData.setVisibility(View.VISIBLE);
                    eceRecyclerView.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        eceList.add(dataSnapshot.getValue(Faculty.class));
                    }
                    facultyAdapter = new FacultyAdapter(FacultyMain.this, eeeList);
                    eceRecyclerView.setAdapter(facultyAdapter);
                    eceRecyclerView.setVisibility(View.VISIBLE);
                    eceNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(FacultyMain.this, "Something went wrong, try again!", Toast.LENGTH_LONG).show();
            }
        });

    }


    private void setEI() {

        eiRecyclerView.setHasFixedSize(true);
        eiRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tempRef = databaseReference.child("Electronics and Instrumentation Engineering");
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                eiList = new ArrayList<>();
                if (!snapshot.exists()) {
                    eiNoData.setVisibility(View.VISIBLE);
                    eiRecyclerView.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        eiList.add(dataSnapshot.getValue(Faculty.class));
                    }
                    facultyAdapter = new FacultyAdapter(FacultyMain.this, eiList);
                    eiRecyclerView.setAdapter(facultyAdapter);
                    eiRecyclerView.setVisibility(View.VISIBLE);
                    eiNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(FacultyMain.this, "Something went wrong, try again!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setEE() {

        eeRecyclerView.setHasFixedSize(true);
        eeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tempRef = databaseReference.child("Electrical Engineering");
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                eeList = new ArrayList<>();
                if (!snapshot.exists()) {
                    eeNoData.setVisibility(View.VISIBLE);
                    eeRecyclerView.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        eeList.add(dataSnapshot.getValue(Faculty.class));
                    }
                    facultyAdapter = new FacultyAdapter(FacultyMain.this, eeList);
                    eeRecyclerView.setAdapter(facultyAdapter);
                    eeRecyclerView.setVisibility(View.VISIBLE);
                    eeNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(FacultyMain.this, "Something went wrong, try again!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setIT() {

        itRecyclerView.setHasFixedSize(true);
        itRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tempRef = databaseReference.child("Information Technology");
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                itList = new ArrayList<>();
                if (!snapshot.exists()) {
                    itNoData.setVisibility(View.VISIBLE);
                    itRecyclerView.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        itList.add(dataSnapshot.getValue(Faculty.class));
                    }
                    facultyAdapter = new FacultyAdapter(FacultyMain.this, itList);
                    itRecyclerView.setAdapter(facultyAdapter);
                    itRecyclerView.setVisibility(View.VISIBLE);
                    itNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(FacultyMain.this, "Something went wrong, try again!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setCSE() {
        csRecyclerView.setHasFixedSize(true);
        csRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tempRef = databaseReference.child("Computer Science");
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                csList = new ArrayList<>();
                if (!snapshot.exists()) {
                    csNoData.setVisibility(View.VISIBLE);
                    csRecyclerView.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        csList.add(dataSnapshot.getValue(Faculty.class));
                    }
                    facultyAdapter = new FacultyAdapter(FacultyMain.this, csList);
                    csRecyclerView.setAdapter(facultyAdapter);
                    csRecyclerView.setVisibility(View.VISIBLE);
                    csNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(FacultyMain.this, "Something went wrong, try again!", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void setDirector() {

        directorRecyclerView.setHasFixedSize(true);
        directorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tempRef = databaseReference.child("Director");
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                directorList = new ArrayList<>();
                if (!snapshot.exists()) {
                    directorNoData.setVisibility(View.VISIBLE);
                    directorRecyclerView.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        directorList.add(dataSnapshot.getValue(Faculty.class));
                    }
                    facultyAdapter = new FacultyAdapter(FacultyMain.this, directorList);
                    directorRecyclerView.setAdapter(facultyAdapter);
                    directorRecyclerView.setVisibility(View.VISIBLE);
                    directorNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(FacultyMain.this, "Something went wrong, try again!", Toast.LENGTH_LONG).show();
            }
        });

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
        eceNoData=findViewById(R.id.ECENoData);
        eiNoData=findViewById(R.id.EINoData);

        //setView recycler views
        directorRecyclerView = findViewById(R.id.directorRecyclerView);
        csRecyclerView = findViewById(R.id.CSERecyclerView);
        itRecyclerView = findViewById(R.id.ITRecyclerView);
        eeRecyclerView = findViewById(R.id.EERecyclerView);
        eeeRecyclerView = findViewById(R.id.EEERecyclerView);
        ceRecyclerView = findViewById(R.id.CERecyclerView);
        meRecyclerView = findViewById(R.id.MERecyclerView);
        eceRecyclerView=findViewById(R.id.ECERecyclerView);
        eiRecyclerView=findViewById(R.id.EIRecyclerView);

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

}