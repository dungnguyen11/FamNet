package com.famnet.famnet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    //Constant
    public static final int RC_SETTING = 200;

    // Views
    ImageView mUserSignOut;
    ImageView mUserPhoto;
    TextView mUserName;
    TextView mUserGroup;
    TextView mUserRole;
    TextView mUserEmail;
    ImageView mUserAddMember;
    ImageView mUserSetting;


    //Firebase
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Views
        mUserSignOut = findViewById(R.id.sign_out_image_view);
        mUserPhoto = findViewById(R.id.user_photo_image_view);
        mUserName = findViewById(R.id.user_name_text_view);
        mUserGroup = findViewById(R.id.user_group_text_view);
        mUserRole = findViewById(R.id.user_role_text_view);
        mUserEmail = findViewById(R.id.user_email_text_view);
        mUserAddMember = findViewById(R.id.user_add_member_image_view);
        mUserSetting = findViewById(R.id.user_setting_image_view);

        //Check User
        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() == null) {
            startActivity(MainActivity.createIntent(this));
            finish();
            return;
        }

        // Set up views

        //Sign out
        mUserSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        // User Photo

        // User Name
        String userName = mFirebaseAuth.getCurrentUser().getDisplayName();
        if (userName != null) {
            mUserName.setText(userName);
        }

        // User Group

        // User Role

        // User Email
        String userEmail = mFirebaseAuth.getCurrentUser().getEmail();
        if (userEmail != null) {
            mUserEmail.setText(userEmail);
        }

        // User Add Member
        mUserAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // User Setting
        mUserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SettingActivity.class);
                startActivityForResult(intent, RC_SETTING);
            }
        });

        //Navigation bar
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.navigation_account);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_chat:
                        Intent intent1 = new Intent(AccountActivity.this, ChatActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_tasks:
                        Intent intent2 = new Intent(AccountActivity.this, TasksActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_personal_task:
                        Intent intent3 = new Intent(AccountActivity.this, PersonalTasksActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_account:
                        break;
                }
                return false;
            }
        });
    }

    private void signOut() {
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(MainActivity.createIntent(AccountActivity.this));
                            finish();
                        } else {
                            Toast.makeText(AccountActivity.this, "Sign out failed", Toast.LENGTH_SHORT);
                        }
                    }
                });
    }



}
