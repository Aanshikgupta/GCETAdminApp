package com.example.adminapp.UploadClasses.FacultyRelatedActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminapp.Models.Faculty;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AddFaculty extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;
    private TextView addImageTV;
    private Button addFacultyButton;
    private EditText facultyName, facultyEmail, facultyPost;
    private CircleImageView facultyImage;
    private Uri imageUri;
    private String imageUrl="";
    private Spinner facultyDepartmentSpinner;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);
        setView();
        setSpinner();
        setListeners();
        setFirebase();
    }

    private void setFirebase() {
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference=databaseReference.child("Faculty");
        storageReference= FirebaseStorage.getInstance().getReference();
        storageReference=storageReference.child("Faculty");
    }

    private void setSpinner() {
        String[] st={"Director","Computer Science","Information Technology","Electrical Engineering","Electrical and Electronics Engineering","Mechanical Engineering","Civil Engineering"};
        SpinnerAdapter spinnerAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,st);
        facultyDepartmentSpinner.setAdapter(spinnerAdapter);
    }

    private void setListeners() {
        addImageTV.setOnClickListener(this);
        addFacultyButton.setOnClickListener(this);
    }

    private void setView() {
        addImageTV = findViewById(R.id.addFacultyImageTV);
        addFacultyButton = findViewById(R.id.addFacultyButton);
        facultyName = findViewById(R.id.facultyName);
        facultyEmail = findViewById(R.id.facultyEmail);
        facultyPost = findViewById(R.id.facultyPost);
        facultyImage = findViewById(R.id.facultyImage);
        facultyDepartmentSpinner=findViewById(R.id.facultyDepartmentSpinner);
        pd=new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addFacultyImageTV:
                selectImage();
                break;
            case R.id.addFacultyButton:
                uploadFacultyData();
                break;
        }

    }

    private void uploadFacultyData() {
        pd.setMessage("Uploading...");
        pd.show();
        String key=databaseReference.push().getKey();
        String facultyDepartment=facultyDepartmentSpinner.getSelectedItem().toString();
        storageReference.child(facultyDepartment).child(key).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                while(!task.isComplete());
                imageUrl=task.getResult().toString();
                addDatatoDB(key,facultyDepartment);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddFaculty.this,"Something went wrong, please try again!",Toast.LENGTH_LONG).show();

            }
        });

    }

    private void addDatatoDB(String key, String facultyDepartment) {
        String name,email,post;
        name=facultyName.getText().toString();
        email=facultyEmail.getText().toString();
        post=facultyPost.getText().toString();
        Faculty faculty=new Faculty(key,name,email,post,facultyDepartment,imageUrl);
        databaseReference.child(facultyDepartment).child(key).setValue(faculty).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(AddFaculty.this,"Successfully uploaded!",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddFaculty.this,"Something went wrong, please try again!",Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            facultyImage.setImageURI(imageUri);
        }else{

        }
    }

    private void selectImage() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), REQUEST_CODE);

    }
}