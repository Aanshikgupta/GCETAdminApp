package com.example.adminapp.UploadClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.adminapp.Adapters.NoticeAdapter;
import com.example.adminapp.HandleClickInterface.OnItemClickListener;
import com.example.adminapp.Models.Notice;
import com.example.adminapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DeleteNotice extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView noticeRecyclerView;
    protected DatabaseReference databaseReference;
    protected StorageReference storageReference;
    private List<Notice> notices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);
        setView();
        setFirebase();
        getAllData();
    }

    private void getAllData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                notices = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    notices.add(0, dataSnapshot.getValue(Notice.class));


                }
                NoticeAdapter noticeAdapter=new NoticeAdapter(DeleteNotice.this,notices,DeleteNotice.this::onClick);
                noticeRecyclerView.setHasFixedSize(true);
                noticeAdapter.notifyItemInserted(0);
                noticeRecyclerView.smoothScrollToPosition(0);
                noticeRecyclerView.setLayoutManager(new LinearLayoutManager(DeleteNotice.this));
                noticeRecyclerView.smoothScrollToPosition(0);
                noticeRecyclerView.setAdapter(noticeAdapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void setFirebase() {
        noticeRecyclerView = findViewById(R.id.noticeRecyclerView);
    }

    private void setView() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = databaseReference.child("Notices");
        storageReference = FirebaseStorage.getInstance().getReference();
        storageReference = storageReference.child("Notices");

    }

    @Override
    public void onClick(int pos) {
        Notice notice = notices.get(pos);
        DatabaseReference ref = databaseReference.child(notice.getKey());
        ref.removeValue();

    }
}