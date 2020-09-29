package com.example.symptracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class FirebaseAuthentication extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private String TAG = "Diamonds";
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symp_home);

        Log.d(TAG, "I am cool AND inside Firebase Authentication");
        auth = FirebaseAuth.getInstance();

        //build AuthUI programmatically (nothing needing to be changed in your XML file
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                //new AuthUI.IdpConfig.GoogleBuilder().build(), commented as no google signin method as of yet
                                new AuthUI.IdpConfig.EmailBuilder().build()))
                        //TODO: Place logo in resources folder
                        //TODO: Change to single list
                        //.setLogo(R.drawable.logo)
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */)
                        .build(),
                RC_SIGN_IN);
    }

    //Handles the sign-in request from firebase and all the possibilities
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK)
            {
                startActivity(new Intent(this, SympHome.class));
                finish();
            }
            else
            {
                // Sign in failed
                if (response == null)
                {
                    // User pressed back button
                    Toast.makeText(this, "Sign-in cancelled", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK)
                {
                    Toast.makeText(this, "Sign-in failed, no network", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }
    }
}