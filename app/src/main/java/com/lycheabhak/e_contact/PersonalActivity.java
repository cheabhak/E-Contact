package com.lycheabhak.e_contact;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.LinearLayout;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
        import android.widget.TextView;

        import net.glxn.qrgen.core.scheme.VCard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.net.URI;

public class PersonalActivity extends AppCompatActivity {
    public String picturePath;
    public String piturePathStored;
    private static int RESULT_LOAD_IMAGE = 1;
    private EditText nameEditText;
    private EditText phonePEditText;
    private EditText mailPEditText;
    private EditText addressEditText1;
    private ImageView userimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
       userimage=(ImageView) findViewById(R.id.profilePicture) ;



       // userimage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                userimage.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                    }
                });
/////////////////////////////////




        nameEditText= (EditText) findViewById(R.id.nameEditText);
        phonePEditText = (EditText) findViewById(R.id.phonePEditText);
        mailPEditText=(EditText) findViewById(R.id.mailPEditText);
        addressEditText1=(EditText) findViewById(R.id.addresEditText1);
        Button generateButton = (Button) findViewById(R.id.generateButton);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genvcard();
            }
        });


        //load informatin from file in sharedPreference
        load();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            userimage.setImageBitmap(getCircleBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(picturePath),300,300,false)));
            SharedPreferences preferences = getSharedPreferences("settings", MODE_WORLD_READABLE);
            preferences.edit().putString("profile", picturePath).apply();

        }

    }

    private void load() {

        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_WORLD_READABLE);
        String vcardString = sharedPreferences.getString("personal", null);
        String profilePic = sharedPreferences.getString("profile",null);
        VCard personal;
        if(vcardString == null) {
            personal = new VCard();
        }else {
            personal = VCard.parse(vcardString);
        }

        if (profilePic == null){
            userimage.setImageBitmap(getCircleBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher),300,300,false)));
        }
        else {
            userimage.setImageBitmap(getCircleBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(profilePic),300,300,false)));
        }

        nameEditText.setText(personal.getName());
        phonePEditText.setText(personal.getPhoneNumber());
        mailPEditText.setText(personal.getEmail());
        addressEditText1.setText(personal.getAddress());
    }

    public void genvcard(){


        // get user input
        String name= nameEditText.getText().toString();
        String phoneP = phonePEditText.getText().toString();
        String mailP= mailPEditText.getText().toString();
        String address1=addressEditText1.getText().toString();



        VCard personal= new VCard();
        personal.setName(name);
        personal.setPhoneNumber(phoneP);
        personal.setEmail(mailP);
        personal.setAddress(address1);
        Log.d("TEST", "vcard " + personal.toString());
        String vcardString = personal.toString();
        //save to sharedpreference
        SharedPreferences preferences = getSharedPreferences("settings", MODE_WORLD_READABLE);
        preferences.edit().putString("personal", vcardString).apply();
        // Go back to MainActivity
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
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
