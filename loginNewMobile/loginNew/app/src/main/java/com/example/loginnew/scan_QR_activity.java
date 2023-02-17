
package com.example.loginnew;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class scan_QR_activity extends Activity {

	private CodeScanner mCodeScanner;
	CodeScannerView scannerView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_qr);
		 scannerView = findViewById(R.id.scanner_view);

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
		{
			ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},101);
		}else
		{
			startScanning();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 101)
		{
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
			{
				startScanning();
			}else{
				Toast.makeText(this, "Camera Permission needed To Continue", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void startScanning() {

		mCodeScanner = new CodeScanner(this, scannerView);
		mCodeScanner.setDecodeCallback(result ->{
					runOnUiThread(() ->{

						if (result.getText().length() > 0 )
						{
							// take user Id from qr and get email and password from firebase the sign in

							FirebaseDatabase.getInstance().getReference().child("Parent").child(result.getText()).get().addOnSuccessListener(snapshot->{

								if (snapshot.exists())
								{
									Parent p = snapshot.getValue(Parent.class);
									FirebaseAuth.getInstance().signInWithEmailAndPassword(p.getEmail(),p.getPassword()).addOnCompleteListener(res->{
										if (res.isSuccessful())
										{
											startActivity(new Intent(scan_QR_activity.this,WelcomeNew.class));
										}else
										{
											Toast.makeText(this, "Sorry Data Stored is Invalid", Toast.LENGTH_SHORT).show();
										}
									});

								}else{
									Toast.makeText(this, "Sorry Couldn't find parent details with this Qrcode", Toast.LENGTH_SHORT).show();
								}
							}).addOnFailureListener(error->{
								Toast.makeText(this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
							});
						}
					});
				}
		);
		scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
	}
	@Override
	protected void onResume() {
		super.onResume();
		mCodeScanner.startPreview();
	}

	@Override
	protected void onPause() {
		mCodeScanner.releaseResources();
		super.onPause();
	}


}
	
	