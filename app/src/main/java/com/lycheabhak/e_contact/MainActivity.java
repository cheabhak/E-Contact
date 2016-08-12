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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.Result;
import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

import java.io.File;

import me.dm7.barcodescanner.zxing.ZXingScannerView;




public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrgen();

        ImageButton startscan = (ImageButton) findViewById(R.id.addnew);
        startscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(getApplicationContext(),CameraActivity.class);
                startActivity(intent1);
            }
        });


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
}

