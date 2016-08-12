package com.lycheabhak.e_contact;

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
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

import net.glxn.qrgen.core.scheme.VCard;

import java.net.URI;

public class PersonalActivity extends AppCompatActivity {


    private EditText nameEditText;
    private EditText phonePEditText;
    private EditText mailPEditText;
    private EditText addressEditText1;

    ImageButton imagePick=(ImageButton) findViewById(R.id.imagePick);
    private final static int SELECT_PHOTO = 12345;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

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

        imagePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        //load informatin from file in sharedPreference
        load();
    }

    private void load() {

        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_WORLD_READABLE);
        String vcardString = sharedPreferences.getString("personal", null);

        VCard personal;
        if(vcardString == null) {
            personal = new VCard();
        }else {
            personal = VCard.parse(vcardString);
        }

        //call back to EditText from file
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

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            imagePick.setImageBitmap(bitmap);
            // Do something with the bitmap

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }
}
