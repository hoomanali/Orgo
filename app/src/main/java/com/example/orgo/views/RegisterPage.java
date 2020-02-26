package com.example.orgo.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orgo.MainActivity;
import com.example.orgo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Constants;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class RegisterPage extends AppCompatActivity {

    public EditText emailId, password;
    Button btnSignUp;
    Button imageUpload;
    Bitmap profilePicture;
    ImageView picture;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mFirebaseAuth = FirebaseAuth.getInstance();
        imageUpload = findViewById(R.id.uploadImage);
        picture = findViewById(R.id.profilePic);
        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnSignUp = findViewById(R.id.button);
        tvSignIn = findViewById(R.id.textView);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(RegisterPage.this,"Fields Are Empty",
                            Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty()) {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                } else if(pwd.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                } else {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd)
                            .addOnCompleteListener(RegisterPage.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()) {
                                        Toast.makeText(RegisterPage.this,
                                                "SignUp Unsuccessful, Please Try Again",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        if(profilePicture != null)
                                            uploadImage(profilePicture, email);
                                        Toast.makeText(RegisterPage.this,
                                                "SignUp Successful",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterPage.this,
                                                MainActivity.class));
                                    }
                                }
                            });
                }
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(i);
            }
        });
        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
                imageUpload.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && null != data) { // we have bitmap from filesystem!
            Uri selectedImage = data.getData();

            InputStream inputStream = null;

            assert selectedImage != null;
            if (ContentResolver.SCHEME_CONTENT.equals(selectedImage.getScheme())) {
                try {
                    inputStream = this.getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                if (ContentResolver.SCHEME_FILE.equals(selectedImage.getScheme())) {
                    try {
                        inputStream = new FileInputStream(Objects.requireNonNull(selectedImage.getPath()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            profilePicture = BitmapFactory.decodeStream(inputStream);
            picture.setImageBitmap(profilePicture);
        }
    }

    public void uploadImage(Bitmap photo, String email) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] photos = baos.toByteArray();
        String encodedPhoto = Base64.encodeToString(photos, Base64.DEFAULT);

        ProfilePhoto profilePhoto = new ProfilePhoto();
        profilePhoto.setEmail(email);
        profilePhoto.setImage(encodedPhoto);
        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("profilePhotos").add(profilePhoto).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "profile photo added to db", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}