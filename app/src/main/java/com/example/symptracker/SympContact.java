package com.example.symptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SympContact extends AppCompatActivity {

    static final int PICK_CONTACT = 1;
    private String contact_number = null;
    private String contact_name = null;
    private String contact_email = null;

    Button addContactBtn;
    TextView contactName;
    TextView contactNumber;
    ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symp_contact);

        addContactBtn = findViewById(R.id.addContactBtn);
        profilePicture = findViewById(R.id.profilePic);

        /* These two should be updated when emergency contact is selected to match the contact's info */
        contactName = findViewById(R.id.contactName);
        contactNumber = findViewById(R.id.contactNumber);

        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);


                /* insert code that happens when button clicked */
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
                // Do something with the phone contact_number
                Log.d("TEST", contact_name);
                Log.d("TEST", contact_number);


                contactNumber.setText(contact_number);
                contactName.setText(contact_name);

                cursor.close();


            }
        }
    }

}