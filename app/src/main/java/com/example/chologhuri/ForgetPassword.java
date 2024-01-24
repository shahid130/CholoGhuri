package com.example.chologhuri;


import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class ForgetPassword extends AppCompatActivity {
    private TextInputEditText ediTextForget;
    private Button buttonForget;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart(){
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null)
        {

        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ediTextForget = findViewById(R.id.editTextForget);
        buttonForget = findViewById(R.id.buttonForget);

        buttonForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ediTextForget.getText().toString();
               if(!email.equals(""))
               {
                   passwordReset(email);
               }
               else
               {
                   Toast.makeText(ForgetPassword.this,"Please Insert Your Correct Email Address",Toast.LENGTH_SHORT).show();
               }
            }
        });

        auth = FirebaseAuth.getInstance();
    }
    public void passwordReset(String email)
    {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgetPassword.this,"Please Check your email",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(ForgetPassword.this,"Please Check your email",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}