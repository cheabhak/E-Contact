package com.lycheabhak.e_contact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

import java.io.File;
import java.io.StringReader;


public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button editbutton = (Button) findViewById(R.id.edit_button);
        Button startscan = (Button) findViewById(R.id.addnew);
        TextView name=(TextView) findViewById(R.id.nameTextView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fontawesome.ttf");
        editbutton.setTypeface(typeface);
        startscan.setTypeface(typeface);


        qrgen();


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
    String picturePath = sharedPreferences.getString("profile",null);
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
    ImageView profileImage= (ImageView) findViewById(R.id.profilePic);
    if (picturePath==null){
        profileImage.setImageBitmap(null);
    }
    else {
        profileImage.setImageBitmap(getCircleBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(picturePath),300,300,false)));
        //getCircleBitmap(bm)
    }
}


    public void NewActivity(View view){
      Intent intent = new Intent(MainActivity.this, PersonalActivity.class);
        startActivity(intent);
        finish();
    }

    ///Convert Bitmap to Circle
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }
}

