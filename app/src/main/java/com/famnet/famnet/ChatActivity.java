package com.famnet.famnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.famnet.famnet.Model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {


    private EditText mEditText;
    private Button mSendButton;
    private TextView mMessages;
    private String mUsername = "anonymous";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        //Check User
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        if (mFirebaseAuth.getCurrentUser() == null) {
//            startActivity(MainActivity.createIntent(this));
//            finish();
//            return;
//        }


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference =
                mFirebaseDatabase.getReference().child("messages");

        mMessages = (TextView) findViewById(R.id.messages_text);
        mEditText = (EditText) findViewById(R.id.editText);
        mSendButton = (Button) findViewById(R.id.button2);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chat chat = new
                        Chat(mEditText.getText().toString(), mUsername, null);

                mMessagesDatabaseReference.push().setValue(chat);

                mMessages.setText(mMessages.getText().toString() + "\n" +
                        mUsername + " " + mEditText.getText().toString());
                mEditText.setText("");
            }
        });

        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Chat chat =
                            dataSnapshot.getValue(Chat.class);

                    mMessages.setText(mMessages.getText().toString() + "\n" +
                            chat.getName() + " " + chat.getText());

//                    Toast.makeText(ChatActivity.this, "New message: " +
//                            chat.getText(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
        }
        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);

        //Navigation bar
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.navigation_chat);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_chat:
                        break;
                    case R.id.navigation_tasks:
                        Intent intent2 = new Intent(ChatActivity.this, TasksActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_personal_task:
                        Intent intent3 = new Intent(ChatActivity.this, PersonalTasksActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_account:
                        Intent intent4 = new Intent(ChatActivity.this, AccountActivity.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }

}
