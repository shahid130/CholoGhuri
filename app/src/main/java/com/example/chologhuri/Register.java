package com.example.chologhuri;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {
    private CircleImageView image;
    private TextInputEditText editEmail,editPass,editUser;
    private Button buttonRegister;
    boolean imageControl = false;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    Uri imageUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        image = findViewById(R.id.circleImageView);
        editEmail = findViewById(R.id.editTextEmailSignup);
        editPass = findViewById(R.id.editTextPasswordSignup);
        editUser = findViewById(R.id.editTextUserNameSignup);
        buttonRegister = findViewById(R.id.buttonregister);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               imagechooser();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email = editEmail.getText().toString();
               String password = editPass.getText().toString();
               String userName = editUser.getText().toString();

               if(!email.equals("") && !password.equals("") && !userName.equals(""))
               {

                   signup(email,password,userName);
               }
            }
        });


    }
    public void imagechooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&& resultCode==RESULT_OK && data!=null)
        {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(image);
            imageControl = true;
        }
        else
        {
            imageControl= false;
        }
    }
   public void signup(String email,String password,final String userName)
    {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                      reference.child("Users").child(Objects.requireNonNull(auth.getUid())).child("userName").setValue(userName);

                      if(imageControl)
                      {
                          //uuid create object from user unique id create different id for each image

                          UUID randomID = UUID.randomUUID();
                          String imageName = "images/"+randomID+".jpg";
                          storageReference.child(imageName).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                              @Override
                              public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                  StorageReference myStorageRef = firebaseStorage.getReference(imageName);
                                  myStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                      @Override
                                      public void onSuccess(Uri uri) {
                                         String filePath = uri.toString();
                                          reference.child("Users").child(auth.getUid()).child("image").setValue(filePath).addOnSuccessListener(new OnSuccessListener<Void>() {
                                              @Override
                                              public void onSuccess(Void unused) {
                                                  Toast.makeText(Register.this,"Add data in database is successfull",Toast.LENGTH_SHORT).show();

                                              }
                                          }).addOnFailureListener(new OnFailureListener() {
                                              @Override
                                              public void onFailure(@NonNull @NotNull Exception e) {
                                                  Toast.makeText(Register.this,"Add data in database is unsucessful",Toast.LENGTH_SHORT).show();
                                              }
                                          });

                                      }
                                  });
                              }
                          });

                      }
                      else
                      {
                          reference.child("Users").child(auth.getUid()).child("image").setValue("null");

                      }
                      Intent intent = new Intent(Register.this,Blank.class);
                      intent.putExtra("userName",userName);
                      startActivity(intent);
                      finish();

                }
                else
                {
                    Toast.makeText(Register.this,"You have already create an account",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}