package com.lycheabhak.e_contact;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Movie;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

import org.w3c.dom.Text;

import java.io.File;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
private ZXingScannerView mScannerView;
    private boolean camera = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        MyDatabase myDatabase = new MyDatabase(getApplicationContext()); // open database
        String[] args = {};
        Cursor cursor = myDatabase.getReadableDatabase().rawQuery("SELECT * FROM personal", args);
        cursor.moveToNext();
        String name = cursor.getString(0); // 0 here is the column index
        String p_phone = cursor.getString(1);
        String w_phone = cursor.getString(2);
        String p_mail = cursor.getString(3);
        String w_mail = cursor.getString(4);
        String all = name + p_mail + p_phone+w_mail+w_phone;
*/

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
/*public void qrgen () {
    Bitmap myBitmap = QRCode.from("www.example.org").bitmap();
    ImageView myImage = (ImageView) findViewById(R.id.imageView);
    myImage.setImageBitmap(myBitmap);
  */
    /*String text =
            "BEGIN:vcard\r\n" +
                    "VERSION:3.0\r\n" +
                    "N:House;Gregory;;Dr;MD\r\n" +
                    "FN:Dr. Gregory House M.D.\r\n" +
                    "END:vcard\r\n";

    VCard vcard = Ezvcard.parse(text).first();
*/
    /*VCard johnDoe = new VCard("abc new")
            .setEmail("abc.doe@example.org")
            .setAddress("John Doe Street 1, 5678 Doestown")
            .setTitle("Mister")
            .setCompany("John Doe Inc.")
            .setPhoneNumber("1234")
            .setWebsite("www.example.org");




    File file = QRCode.from(vcard).file();
    Log.d("exc", file.toString());

}
*/
    public void NewActivity(View view){
        Intent intent = new Intent(MainActivity.this, PersonalActivity.class);
        startActivity(intent);
    }

    public void QrScanner(View view){
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
       // mScannerView.startCamera();// Start camera
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
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
        //mScannerView.resumeCameraPreview(this);
        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }

}

