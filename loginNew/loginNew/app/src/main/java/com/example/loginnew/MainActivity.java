package com.example.loginnew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView register;
    private EditText editTextEmail,editTextPassword;
    private Button loginbtn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ImageButton QRbtn;
    private String parentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("نٌـظـم");

        loginbtn =  findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(this);

        editTextEmail =  findViewById(R.id.email);
        editTextPassword =  findViewById(R.id.password);

        progressBar =  findViewById(R.id.progressBar);


        mAuth = FirebaseAuth.getInstance();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /*case R.id.register:
                startActivity(new Intent(this, SignupNew.class));
                break;*/
            case R.id.loginbtn:
                userLogin();
                break;
        }

    }//onclick


    private void userLogin() {
        String email1 = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();



        if(email1.isEmpty()){
            editTextEmail.setError("E-mail is required!!");
            editTextEmail.requestFocus();
            return;
        }//if

        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            editTextEmail.setError("Enter a valid E-mail!!");
            editTextEmail.requestFocus();
            return;
        }//if

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!!");
            editTextPassword.requestFocus();
            return;
        }//if

        if(password.length()<6){
            editTextPassword.setError("Enter 10 characters password!!");
            editTextPassword.requestFocus();
            return;
        }//if

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email1,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                parentId = task.getResult().getUser().getUid();
                FirebaseDatabase.getInstance().getReference().child("Parent").child(parentId).get().addOnSuccessListener(snapShot ->{
                           /// get Parent Details with id
                    int Id;
                    FirebaseDatabase.getInstance().getReference().child("Student").get().addOnSuccessListener(dataSnapshot -> {
                        for (DataSnapshot snapShots:dataSnapshot.getChildren()) {

                        }
                    });
                }).addOnFailureListener(error -> {
                    Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
                Intent intent = new Intent(MainActivity.this, WelcomeNew.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Successfully logged-in", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(MainActivity.this, "Failed to login!! please check your credentials", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), QRloginNew.class);
        startActivityForResult(intent, 0);
        return true;
    }
    }


