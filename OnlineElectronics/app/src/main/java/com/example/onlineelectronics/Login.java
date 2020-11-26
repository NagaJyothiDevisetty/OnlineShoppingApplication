package com.example.onlineelectronics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private Button loginBtn;
        private EditText loginemail, loginpassword;
        FirebaseAuth Auth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            loginBtn = (Button) findViewById(R.id.signIn);
            loginemail = (EditText) findViewById(R.id.email);
            loginpassword = (EditText) findViewById(R.id.password);
            Auth=FirebaseAuth.getInstance();
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = loginemail.getText().toString();
                    String password = loginpassword.getText().toString();
                    if(TextUtils.isEmpty(email)){
                        loginemail.setError("Email field should not be empty");
                        return;
                    }
                    if(TextUtils.isEmpty(password)){
                        loginpassword.setError("Password is required");
                        return;
                    }
                    if(password.length() >5){
                        loginpassword.setError("Password must be more than 5 characters");
                        return;
                    }
                    // authenticate the user
                    Auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(Login.this,HomePage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Error" +task.getException().getMessage(),Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            });
        }
    }

