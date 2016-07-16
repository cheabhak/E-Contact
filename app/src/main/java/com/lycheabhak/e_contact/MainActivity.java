package com.lycheabhak.e_contact;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.LineHeightSpan;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.Result;
import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

import java.io.File;

import me.dm7.barcodescanner.zxing.ZXingScannerView;




public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
private ZXingScannerView mScannerView;



    private boolean camera = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrgen();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        if(camera==true){
            mScannerView.stopCamera();
            startActivity(intent);
        }
        else {
          finish();
        }
        //System.exit(0);

    }
public void qrgen () {

    SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
    String vcardString = sharedPreferences.getString("personal", null);
    VCard personal;
    if(vcardString == null) {
        personal = new VCard();
    }else {
        personal = VCard.parse(vcardString);
    }
    TextView name=(TextView) findViewById(R.id.nameTextView);
    name.setText(personal.getName());
    Bitmap myBitmap = QRCode.from(personal).withSize(500,500).bitmap();
    ImageView myImage = (ImageView) findViewById(R.id.img_qr);
    myImage.setImageBitmap(myBitmap);
}




    public void NewActivity(View view){
        Intent intent = new Intent(MainActivity.this, PersonalActivity.class);
        startActivity(intent);
    }

    public void QrScanner(View view){
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();// Start camera
        camera=true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView != null){
            mScannerView.stopCamera();   // Stop camera on pause
        }

    }


    @Override
    public void handleResult(Result rawResult) {
        Log.e("handler", rawResult.getText());
        VCard contact=VCard.parse(rawResult.getText());


        // Do something with the result here
/*
         // Prints scan results
        //Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
        //mScannerView.resumeCameraPreview(this);
        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    */
    }

}

