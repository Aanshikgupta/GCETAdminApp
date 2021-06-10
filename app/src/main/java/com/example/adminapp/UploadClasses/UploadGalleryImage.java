package com.example.adminapp.UploadClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.adminapp.Models.GalleryImage;
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

import java.net.URI;

import okio.Okio;

public class UploadGalleryImage extends AppCompatActivity implements View.OnClickListener {
    private CardView selectGalleryImageCard;
    private Spinner spinnerCateogry;
    private Button uploadGalleryImageButton;
    private ImageView previewImage;
    private  final int REQUEST_CODE_GALLERY_IMAGE=1;
    private Uri selectedGalleryImageUri;
    private String selectedImageCategory="";
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String imageUrl="";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_gallery_image);
        setView();
        setSpinner();
        setFirebase();
        setListeners();
    }

    private void setFirebase() {
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference=databaseReference.child("Gallery");
        storageReference= FirebaseStorage.getInstance().getReference();
        storageReference=storageReference.child("Gallery");

    }

    private void setListeners() {
        selectGalleryImageCard.setOnClickListener(this);
        uploadGalleryImageButton.setOnClickListener(this);
    }

    private void setSpinner() {
        String[] st={"Fresher's day","Convocation","Independence Day","Holi","Diwali"};
        SpinnerAdapter spinnerAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,st);
        spinnerCateogry.setAdapter(spinnerAdapter);
    }

    private void setView() {
        selectGalleryImageCard=findViewById(R.id.selectGalleryImageCard);
        spinnerCateogry=findViewById(R.id.galleryImageSpinner);
        uploadGalleryImageButton=findViewById(R.id.uploadGalleryButton);
        previewImage=findViewById(R.id.previewGalleryImage);
        pd = new ProgressDialog(UploadGalleryImage.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selectGalleryImageCard:
                selectGalleryImage();
                break;
            case R.id.uploadGalleryButton:
                uploadGalleryImage();
                break;
        }

    }

    private void uploadGalleryImage() {
        if(selectedGalleryImageUri==null){
            Toast.makeText(UploadGalleryImage.this,"Please select an image!",Toast.LENGTH_LONG).show();
            return;

        }
        pd.setMessage("Uploading...");
        pd.show();
        selectedImageCategory= spinnerCateogry.getSelectedItem().toString();
//        Toast.makeText(this,selectedImageCategory,Toast.LENGTH_LONG).show();
        String key=databaseReference.push().getKey();
        storageReference.child(selectedImageCategory).child(key).putFile(selectedGalleryImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                while(!task.isComplete());
                imageUrl=task.getResult().toString();
//                Toast.makeText(UploadGalleryImage.this,imageUrl,Toast.LENGTH_LONG).show();
                addImageToDb(key);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadGalleryImage.this,"Something went wrong, try again!",Toast.LENGTH_LONG).show();

            }
        });


    }

    private void addImageToDb(String key) {
        GalleryImage galleryImage=new GalleryImage(key,imageUrl);
        databaseReference.child(selectedImageCategory).child(key).setValue(galleryImage).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(UploadGalleryImage.this,"Success!",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadGalleryImage.this,"Something went wrong, try again!",Toast.LENGTH_LONG).show();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_GALLERY_IMAGE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            selectedGalleryImageUri=data.getData();
            previewImage.setImageURI(selectedGalleryImageUri);
        }else{

        }
    }

    private void selectGalleryImage() {
        Intent i=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Select Image"),REQUEST_CODE_GALLERY_IMAGE);
    }
}