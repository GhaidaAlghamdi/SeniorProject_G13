package com.example.loginnew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.FirebaseDatabase;

public class SignupNew extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView banner,registeruser;
    private EditText editTextfullnaame,editTextphone,editTextemail,editTextpassword;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_new);

        mAuth = FirebaseAuth.getInstance();
        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registeruser =(Button) findViewById(R.id.signup);
        registeruser.setOnClickListener(this);

        editTextemail = (EditText) findViewById(R.id.emailedit);
        editTextfullnaame = (EditText) findViewById(R.id.nameedit);
        editTextphone = (EditText) findViewById(R.id.phoneedit);
        editTextpassword = (EditText) findViewById(R.id.passwordedit);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        registerUser();
    }

    private void registerUser() {
        String email1 = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String phone = editTextphone.getText().toString().trim();
        String name = editTextfullnaame.getText().toString().trim();

        if(name.isEmpty()){
            editTextfullnaame.setError("full name is required");
            editTextfullnaame.requestFocus();
            return;
        }//if

        if(password.isEmpty()){
            editTextpassword.setError("password is required");
            editTextpassword.requestFocus();
            return;
        }//if

        if(email1.isEmpty()){
            editTextemail.setError("email is required");
            editTextemail.requestFocus();
            return;
        }//if

        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            editTextemail.setError("Enter a valid email");
            editTextemail.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            editTextphone.setError("phone is required");
            editTextphone.requestFocus();
            return;
        }//if

        if(password.length()<10){
            editTextpassword.setError("Enter at least 10 characters password");
            editTextpassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email1,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(email1,name,phone,password);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                Toast.makeText(SignupNew.this, "user ha been successful registerd", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                            else {

                                                Toast.makeText(SignupNew.this, "failed to register", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        }  else {

                            Toast.makeText(SignupNew.this, "failed to register", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}