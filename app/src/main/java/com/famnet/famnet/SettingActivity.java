package com.famnet.famnet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SettingActivity extends AppCompatActivity {

    // Views
    ImageView mUserPhoto;
    EditText mUserName;
    EditText mUserGroup;
    EditText mUserEmail;
    EditText mUserPassword;

    //Firebase
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mCurrentUser;
    FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Views
        mUserPhoto = findViewById(R.id.user_photo_setting);
        mUserGroup = findViewById(R.id.user_group_setting_text);
        mUserEmail = findViewById(R.id.user_email_setting_text);
        mUserPassword = findViewById(R.id.user_password_setting_text);

        // Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();

        // Set up views
            // Name
            // Group
            // Email
            // Password





    }





















}

