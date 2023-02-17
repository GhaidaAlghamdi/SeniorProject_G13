package com.example.loginnew;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EarlyPermission extends AppCompatActivity {
    private EditText stdname, stdgrade, time1;
    private Button btnSend;
    StudentInfo stdInfo;
    private FirebaseDatabase db;
    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_early_permission);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("نٌـظـم");
        db = FirebaseDatabase.getInstance();
        root = db.getReference("Early permission");
        stdInfo = new StudentInfo();

        stdname = (EditText) findViewById(R.id.studentname);
        stdgrade = (EditText) findViewById(R.id.studentgrade);
        time1 = (EditText) findViewById(R.id.time);
    }

    public void sendToDB(View view) {
        String name = stdname.getText().toString();
        String grade = stdgrade.getText().toString();
        String time = time1.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(grade)|| TextUtils.isEmpty(time)) {
            Toast.makeText(EarlyPermission.this, "الرجاء التأكد من إضافة اسم الطالب, الصف, الوقت",
                    Toast.LENGTH_SHORT).show();
        } else {
            sendDatatoFirebase(name, grade, time);
        }
    }

    private void sendDatatoFirebase(String name, String grade, String time) {
        stdInfo.setStdName(name);
        stdInfo.setStdGrade(grade);
        stdInfo.settime(time);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                root.child(stdInfo.getStdName()).setValue(stdInfo);

                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference().child("Early permission").child(userId).setValue(stdInfo);

                // after adding this data we are showing toast message.

                Toast.makeText(EarlyPermission.this, "تم الارسال بنجاح", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(EarlyPermission.this, "حدث خطأ " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), WelcomeNew.class);
        startActivityForResult(intent, 0);
        return true;
    }
}
