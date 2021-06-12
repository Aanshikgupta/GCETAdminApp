package com.example.adminapp.UploadClasses.FacultyRelatedActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adminapp.Models.Faculty;
import com.example.adminapp.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class UpdateFaculty extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;
    private Uri imageUri;
    private EditText facultyName, facultyEmail, facultyPost;
    private ImageView facultyImage;
    private Button updateButton, deleteButton;
    private TextView updateFacultyImageTextView;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String newImageUrl="";
    private ProgressDialog pd;
    private boolean flag=false;
    private String facultyNameString, key,facultyDepartment,facultyEmailString, facultyPostString, facultyImageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);
        flag=false;
        setView();
        setUpFirebase();
        getAllFacultyData();
        setAllData();
        setListeners();
    }

    private void setUpFirebase() {
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference=databaseReference.child("Faculty");
        storageReference= FirebaseStorage.getInstance().getReference();
        storageReference=storageReference.child("Faculty");
    }

    private void setListeners() {
        updateFacultyImageTextView.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    private void setAllData() {
        facultyName.setText(facultyNameString);
        facultyEmail.setText(facultyEmailString);
        facultyPost.setText(facultyPostString);
        String url = facultyImageUrl;
        if (!(url.equalsIgnoreCase("")))
            Glide.with(UpdateFaculty.this).load(url).into(facultyImage);
    }

    private void getAllFacultyData() {
        Intent intent = getIntent();
        facultyNameString = intent.getStringExtra("FacultyName");
        facultyEmailString = intent.getStringExtra("FacultyEmail");
        facultyPostString = intent.getStringExtra("FacultyPost");
        facultyImageUrl = intent.getStringExtra("FacultyImageUrl");
        facultyDepartment = intent.getStringExtra("FacultyDepartment");
        newImageUrl=facultyImageUrl;
        key = intent.getStringExtra("Key");
    }

    private void setView() {
        facultyName = findViewById(R.id.updateFacultyNameEditText);
        facultyEmail = findViewById(R.id.updateFacultyEmailEditText);
        facultyPost = findViewById(R.id.updateFacultyPostEditText);
        updateButton = findViewById(R.id.updateFacultyUpdateButton);
        deleteButton = findViewById(R.id.updateFacultyDeleteButton);
        updateFacultyImageTextView = findViewById(R.id.updateFacultyUpdateImageTextButton);
        facultyImage = findViewById(R.id.updateFacultyImageView);
        pd=new ProgressDialog(UpdateFaculty.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            facultyImage.setImageURI(imageUri);
            flag = true;
        } else {

        }
    }

    private void uploadImage() {
        storageReference.child(facultyDepartment).child(key).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                while(!task.isComplete());
                newImageUrl=task.getResult().toString();
                updateInDB();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateFaculty.this,"Something went wrong, try again!",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateInDB() {
        facultyNameString=facultyName.getText().toString();
        facultyEmailString=facultyEmail.getText().toString();
        facultyPostString=facultyPost.getText().toString();
        Faculty faculty=new Faculty(key,facultyNameString,facultyEmailString,facultyPostString,facultyDepartment,newImageUrl);
        databaseReference.child(facultyDepartment).child(key).setValue(faculty).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(UpdateFaculty.this,"Updated Successfully!",Toast.LENGTH_LONG).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateFaculty.this,"Something went wrong, try again!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void selectImage() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), REQUEST_CODE);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.updateFacultyUpdateImageTextButton:
                selectImage();
                break;
            case R.id.updateFacultyUpdateButton:
                updateFacultyData();
                break;
            case R.id.updateFacultyDeleteButton:
                deleteFaculty();
                break;
        }

    }

    private void deleteFaculty() {
        pd.setMessage("Deleting...");
        DatabaseReference ref=databaseReference.child(facultyDepartment).child(key);
        ref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(UpdateFaculty.this,"deleted successfully!",Toast.LENGTH_LONG).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateFaculty.this,"Something went wrong, try again!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateFacultyData() {
        pd.setMessage("Updating...");
        pd.show();
        if(flag){
            uploadImage();
        }else
            updateInDB();
    }
}