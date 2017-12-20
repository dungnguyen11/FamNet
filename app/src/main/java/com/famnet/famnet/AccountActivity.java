package com.famnet.famnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famnet.famnet.Model.Family;
import com.famnet.famnet.Model.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {

    //Constant
    private static final String TAG = "AccountActivity";
    public static final int RC_SETTING = 200;

    // Views
    ImageView mUserSignOut;
    ImageView mUserPhoto;
    TextView mUserName;
    TextView mUserFamily;
    TextView mUserRole;
    TextView mUserEmail;
    ImageView mUserAddMember;
    ImageView mUserSetting;


    //Firebase
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase mFirebaseDatabase;
    FirebaseUser mCurrentUser;
    DatabaseReference mCurrentUserReference;

    // Properties
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Views
        mUserSignOut = findViewById(R.id.sign_out_image_view);
        mUserPhoto = findViewById(R.id.user_photo_image_view);
        mUserName = findViewById(R.id.user_name_text_view);
        mUserFamily = findViewById(R.id.user_family_text_view);
        mUserEmail = findViewById(R.id.user_email_text_view);
        mUserAddMember = findViewById(R.id.user_add_member_image_view);
        mUserSetting = findViewById(R.id.user_setting_image_view);

        // Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCurrentUserReference = mFirebaseDatabase.getReference("Users/" + mCurrentUser.getUid());
        mCurrentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                Log.d(TAG, "" + user.getName() + ", " + user.getEmail());

                if (user != null) {
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
                    String userName = user.getName();
                    if (userName != null) {
                        mUserName.setText(userName);
                    }

                    // User Family
                    Family family = user.getFamily();
                    if (family != null) {
                        mUserFamily.setText(family.getName());
                    }

                    // User Email
                    String userEmail = user.getEmail();
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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Check if user sign in
        if (mCurrentUser == null) {
            startActivity(MainActivity.createIntent(this));
            finish();
            return;
        }

//        Log.d(TAG, "" + user.getName() + ", " + user.getEmail());





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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO: take result and update data on activity
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
