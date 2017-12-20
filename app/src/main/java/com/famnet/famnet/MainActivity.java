package com.famnet.famnet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    Button signInButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //Check if the user already login
        if (mAuth.getCurrentUser() != null){
            //Move to signed in app
            startActivity(TasksActivity.createIntent(this));
            finish();
        }

        //If the user does not log in, allow user to login with FirebaseUI
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder().build(),
                        RC_SIGN_IN
                );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleSignInResponse(resultCode, data);
        return;
    }

    private void handleSignInResponse(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        Toast toast;


        if (resultCode == RESULT_OK){ //Successfully sign in
            startActivity(TasksActivity.createIntent(this));
            finish();
            return;
        } else { //Failed to sign in

            if (response == null) { //User pressed back button
                toast = Toast.makeText(this, "Sign in was cancelled", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                toast = Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                toast = Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

        }

        toast = Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT);
        toast.show();
    }

    public static Intent createIntent(Context context){
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        return intent;
    }

























}
