package com.example.symptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.prefs.Preferences;

public class SympContact extends AppCompatActivity {
    static final int PICK_CONTACT = 1;
    private String contact_number = null;
    private String contact_name = null;
    private String contact_email = null;
    //private DBHandler db;
    private String CONTACT_NUMBER_KEY = "";
    private String CONTACT_NAME_KEY =  "";
    private String resPref;
    Button addContactBtn;
    TextView contactName;
    TextView contactNumber;
    ImageView profilePicture;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symp_contact);

        CONTACT_NAME_KEY = getResources().getString(R.string.contact_name_key);
        CONTACT_NUMBER_KEY = getResources().getString(R.string.contact_number_key);
        //Grabs the preferences file from the strings.xml to avoid hardcoding.
        resPref = getResources().getString(R.string.contactFile);

        addContactBtn = findViewById(R.id.addContactBtn);
        profilePicture = findViewById(R.id.profilePic);

        /* These two are updated when emergency contact is selected to match the contact's info */
        contactName = findViewById(R.id.contactName);
        contactNumber = findViewById(R.id.contactNumber);

        //db = new DBHandler(this);

        //Creates a preference object to get stored values
        SharedPreferences pref = getSharedPreferences(resPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String defaultValue = "default";
        if(!pref.getString(CONTACT_NAME_KEY, defaultValue).equals(defaultValue))
        {
            String phName = pref.getString(CONTACT_NAME_KEY, defaultValue);
            String phNumber = pref.getString(CONTACT_NUMBER_KEY, defaultValue);

            contactNumber.setText(phNumber);
            contactName.setText(phName);
        }

        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_CONTACT && resultCode == RESULT_OK)
        {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                //contact_number = cursor.getString(numberIndex);
                contact_name = cursor.getString(numberIndex);
                numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                contact_number = cursor.getString(numberIndex);

                Log.d("TEST", contact_name);
                Log.d("TEST", contact_number);

                SharedPreferences pref = getSharedPreferences(resPref, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                //Contact number is the key as it is unique.
                //The contact name cannot be guaranteed to be unique.

                editor.putString(CONTACT_NUMBER_KEY, contact_number);
                editor.putString(CONTACT_NAME_KEY, contact_name);
                editor.commit();

                contactNumber.setText(contact_number);
                contactName.setText(contact_name);

                cursor.close();
            }
        }
    }

}