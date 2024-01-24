package com.example.chologhuri;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class Login extends AppCompatActivity {

    private TextInputEditText editTextEmail,editTextPasswod;
    private Button buttonLogin,buttonSignup;
    private TextView textViewForget;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    //age account create kore thakle direct oi account e jabe

    @Override
    protected void onStart(){
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null)
        {
            Intent intent = new Intent(Login.this,Blank.class);
            startActivity(intent);

        }
    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPasswod= findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.btn_login);
        buttonSignup = findViewById(R.id.btn_signup);
        textViewForget  = findViewById(R.id.textViewForget);
        auth = FirebaseAuth.getInstance();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= editTextEmail.getText().toString();
                String password = editTextPasswod.getText().toString();

                if(!email.equals("")&& !password.equals(""))
                {
                   signin(email,password);
                }
                else{
                    Toast.makeText(Login.this,"Please enter an email and password",Toast.LENGTH_SHORT).show();
                }

            }
        });
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(Login.this,Register.class);
               startActivity(intent);
            }
        });
        textViewForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ForgetPassword.class);
                startActivity(intent);
            }
        });

    }
    public  void signin(String email,String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(Login.this,Blank.class);
                    Toast.makeText(Login.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Sign in not successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }
