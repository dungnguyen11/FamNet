package com.famnet.famnet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famnet.famnet.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingActivity extends AppCompatActivity {

    // Constant
    private static final String TAG = "SettingActivity";

    // Views
    ImageView mUserPhoto;
    EditText mUserName;
    EditText mUserFamily;
    TextView mUserEmail;
    EditText mUserPassword;
    Button mSaveButton;

    //Firebase
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mCurrentUser;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mCurrentUserReference;

    // Properties
    User user;
    String newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Views
        mUserName = findViewById(R.id.user_name_setting);
        mUserPhoto = findViewById(R.id.user_photo_setting);
        mUserFamily = findViewById(R.id.user_family_setting_text);
        mUserEmail = findViewById(R.id.user_email_setting_text);
        mUserPassword = findViewById(R.id.user_password_setting_text);
        mSaveButton = findViewById(R.id.save_setting_button);

        // Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCurrentUserReference = mFirebaseDatabase.getReference("Users").child(mCurrentUser.getUid());

        // Set up views
        mCurrentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    // Name
                    mUserName.setText(user.getName());

                    //TODO: implement family
//                    // Family
//                    mUserFamily.setText(user.getFamily().getName());

                    // Email
                    mUserEmail.setText(user.getEmail());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Cannot read user data in " + TAG);
            }
        });

        mUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newPassword = s.toString();
            }
        });


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Change password
                if (newPassword != null) {
                    Log.d(TAG, "Verify: " + mCurrentUser.isEmailVerified());
                    Log.d(TAG, "newPassword: " + newPassword);
                    Log.d(TAG, "Current User: " + mCurrentUser.getDisplayName() + " " + mCurrentUser.getEmail());
                    mCurrentUser.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User password update successfully");
                                        Toast.makeText(SettingActivity.this, "Update password successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d(TAG, "User password update FAILED");
                                        Toast.makeText(SettingActivity.this, "Cannot update password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                //TODO: implement family
                User newUser = new User(user.getId(),
                        mUserName.getText().toString(),
                        null,
                        mUserEmail.getText().toString());

                mCurrentUserReference.setValue(newUser);
                Intent returnIntent = new Intent(SettingActivity.this, AccountActivity.class);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }



}

