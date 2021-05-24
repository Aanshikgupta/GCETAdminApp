package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class UploadNotice extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECT_PICTURE =1000 ;
    private CardView uploadImage;
    private ImageView IVPreviewImage;
    private EditText noticeTitle;
    private Button upload;
    private Uri selectedImageUri;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String downloadUrl="";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);
        setView();
        setOnClicks();
        firebaseUploads();
    }

    private void firebaseUploads() {
        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();

    }

    private void setView() {

        uploadImage=findViewById(R.id.UploadImage);
        IVPreviewImage=findViewById(R.id.previewImage);
        noticeTitle=findViewById(R.id.noticeTitle);
        upload=findViewById(R.id.uploadNotice);
        pd=new ProgressDialog(this);
        
    }

    private void setOnClicks(){
        uploadImage.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.UploadImage:
                openGallery();
                break;
            case R.id.uploadNotice:

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void openGallery() {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
}