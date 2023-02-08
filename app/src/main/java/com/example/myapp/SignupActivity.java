package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signupEmail, signupPassword;
    private Button signupButton;
    private TextView loginRedius;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.setTitle("Signup Page");

        signupEmail = findViewById(R.id.signup_email);
        signupPassword= findViewById(R.id.signup_pass);
        signupButton = findViewById(R.id.signup_btn);
        loginRedius = findViewById(R.id.loginRedius);
        progressBar = findViewById(R.id.progressbar);

        loginRedius.setOnClickListener(this);
        signupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.signup_btn:

                userRegister();

                break;

            case R.id.loginRedius:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

                break;
        }

    }

    private void userRegister() {
        String email = signupEmail.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();

        if(email.isEmpty()){
            signupEmail.setError("Enter a email address");
            signupEmail.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signupEmail.setError("Enter a valid email address");
            signupEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            signupPassword.setError("Enter your Password");
            signupPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            signupPassword.setError("Password length should be 6");
            signupPassword.requestFocus();
            return;

        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

            if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Signup is Successful",Toast.LENGTH_SHORT).show();
                }
                else {
                //Toast.makeText(getApplicationContext(),"Signup not is Successful",Toast.LENGTH_SHORT).show();

                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(getApplicationContext(), "User is already exist", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            }
        });



    }


}