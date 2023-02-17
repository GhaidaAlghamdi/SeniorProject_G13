package com.example.loginnew;


import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;

public class QRloginNew extends AppCompatActivity {
    private Button btn_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrlogin_new);

        btn_scan =findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(v->
        {
            startActivity(new Intent(QRloginNew.this,scan_QR_activity.class));
//            scanCode();
        });
    }

//    private void scanCode()
//    {
//        ScanOptions options = new ScanOptions();
//        options.setPrompt("Volume up to flash on");
//        options.setBeepEnabled(true);
//        options.setOrientationLocked(true);
//        options.setCaptureActivity(CaptureAct.class);
//        barLaucher.launch(options);
//    }

//    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result->
//    {
//        if(result.getContents() !=null)
//        {
//            AlertDialog.Builder builder = new AlertDialog.Builder(QRloginNew.this);
//            builder.setTitle("Result");
//            builder.setMessage(result.getContents());
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
//            {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i)
//                {
//                    dialogInterface.dismiss();
//                }
//            }).show();
//        }
//    });

    public void onClickBack(View view) {
        startActivity(new Intent(QRloginNew.this,MainActivity.class));
    }
}