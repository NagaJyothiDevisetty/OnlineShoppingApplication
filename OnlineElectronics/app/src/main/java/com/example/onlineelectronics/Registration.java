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
import android.widget.Toast;

import com.example.onlineelectronics.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Registration extends AppCompatActivity {

    private Button createAccountBtn;
    private EditText fName,lName,Phone,emailId,userPassword;
    private ProgressDialog loadingBar;
    FirebaseAuth Auth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fName = (EditText) findViewById(R.id.firstName);
        lName = (EditText) findViewById(R.id.lastName);
        Phone = (EditText) findViewById(R.id.phnumber);
        emailId = (EditText) findViewById(R.id.email);
        userPassword = (EditText) findViewById(R.id.password);
        createAccountBtn = (Button) findViewById(R.id.createAccount);


        db = FirebaseFirestore.getInstance();
        Auth = FirebaseAuth.getInstance();

        if(Auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Registration.this,HomePage.class);
                startActivity(intent);
                CreateAccount();
            }
        });
    }

        private void CreateAccount() {
            String fname=fName.getText().toString();
            String lname=lName.getText().toString();
            String phone=Phone.getText().toString();
            String email=emailId.getText().toString();
            String password=userPassword.getText().toString();

            db = FirebaseFirestore.getInstance();
            Auth = FirebaseAuth.getInstance();


            createAccountBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String fname = fName.getText().toString();
                    String lname = lName.getText().toString();
                    String phone=Phone.getText().toString();
                    String email = emailId.getText().toString();
                    String password = userPassword.getText().toString();
                    if(TextUtils.isEmpty(email)){
                        Toast.makeText(Registration.this,"Email is required",Toast.LENGTH_LONG).show();
                    }
                    if(TextUtils.isEmpty(password)){
                        Toast.makeText(Registration.this,"Password is required",Toast.LENGTH_LONG).show();
                    }
                    if(password.length() >5){
                        Toast.makeText(Registration.this,"Password must be above 5 charecters",Toast.LENGTH_LONG).show();
                    }

                    Auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                addData(fname, lname, phone, email, password);
                                Toast.makeText(Registration.this,"User Created",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),Login.class));
                            } else{
                                Toast.makeText(Registration.this, "Error" +task.getException().getMessage(),Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }
            });

        }

    public void addData(String fName,String lName, String Phone,String emailId, String userPassword) {
        Users users = new Users(fName, lName, Phone, emailId, userPassword);
        db.collection("users")
                .add(users)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Users recorded", Toast.LENGTH_LONG).show();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        }
    }


