package com.lycheabhak.e_contact;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;

import net.glxn.qrgen.core.scheme.VCard;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        QrScanner(mScannerView);
    }

    public void QrScanner(View view) {
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();// Start camera

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView != null) {
            mScannerView.stopCamera();   // Stop camera on pause
        }

    }


    @Override
    public void handleResult(Result rawResult) {
        Log.e("handler", rawResult.getText());
        VCard contact = VCard.parse(rawResult.getText());
        addcontact(contact);
        finish();

    }
    private void addcontact(VCard contact) {
        String name=contact.getName();
        String phone=contact.getPhoneNumber();
        String email=contact.getEmail();
        String address=contact.getAddress();
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME,name );
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
        startActivity(intent);
    }

}