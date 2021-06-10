package com.example.adminapp.UploadClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminapp.Models.Ebook;
import com.example.adminapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class UploadEbook extends AppCompatActivity implements View.OnClickListener {


    private static final int REQUEST_CODE = 1;
    private CardView selectPdf;
    private Button uploadPdfButton;
    private EditText pdfTitle;
    private TextView pdfName;
    private Uri pdfUri;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String pdfUrl="";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ebook);
        setView();
        setFirebase();
        pdfName.setVisibility(View.GONE);
        setListeners();
    }

    private void setFirebase() {
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference=databaseReference.child("Ebooks");
        storageReference= FirebaseStorage.getInstance().getReference();
        storageReference=storageReference.child("Ebooks");
    }

    private void setListeners() {
        selectPdf.setOnClickListener(this);
        uploadPdfButton.setOnClickListener(this);
    }

    private void setView() {
        selectPdf = findViewById(R.id.selectPdfCard);
        uploadPdfButton = findViewById(R.id.uploadPdfButton);
        pdfTitle = findViewById(R.id.pdfTitle);
        pdfName = findViewById(R.id.pdfName);
        pd=new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectPdfCard:
                getPdfFromDevice();
                break;
            case R.id.uploadPdfButton:
                uploadPdf();
                break;
        }

    }

    private void uploadPdf() {
        if(pdfUri==null){
            Toast.makeText(this,"Please sleect a pdf",Toast.LENGTH_LONG).show();
            return;
        }
        pd.setMessage("Uploading..");
        pd.show();
        String key=databaseReference.push().getKey();
        storageReference.child(key).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                while(!task.isComplete());
                pdfUrl=task.getResult().toString();
                addDataToDb(key);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadEbook.this,"Something went wrong, try again!",Toast.LENGTH_LONG).show();

            }
        });
    }

    private void addDataToDb(String key) {
        Ebook ebook=new Ebook(key,pdfUrl,pdfTitle.getText().toString());
        databaseReference.child(key).setValue(ebook).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(UploadEbook.this,"Successfully uploaded!",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadEbook.this,"Something went wrong, try again!",Toast.LENGTH_LONG).show();

            }
        });
    }

    private void getPdfFromDevice() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            pdfUri = data.getData();
            String uriString = pdfUri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;
            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(pdfUri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }
            pdfName.setVisibility(View.VISIBLE);
            pdfName.setText(displayName);
//            Toast.makeText(this, "Got the pdf", Toast.LENGTH_LONG).show();
        } else {

        }
    }
}