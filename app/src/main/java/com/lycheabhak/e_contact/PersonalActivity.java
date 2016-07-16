package com.lycheabhak.e_contact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import net.glxn.qrgen.core.scheme.VCard;

public class PersonalActivity extends AppCompatActivity {


    private EditText nameEditText;
    private EditText phonePEditText;
    private EditText mailPEditText;
    private EditText addressEditText1;


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
}
