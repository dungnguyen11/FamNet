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

    // Views
    private EditText mEditText;
    private Button mSendButton;
    private TextView mLeftMessages;
    private TextView mRightMessages;
    private String mUsername;

    // Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private ChildEventListener mChildEventListener;

    // Properties
    private String leftMessageText;
    private String rightMessageText;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Initialize
            //Views
        mLeftMessages = findViewById(R.id.messages_left_text_view);
        mRightMessages = findViewById(R.id.message_right_text_view);
        mEditText = findViewById(R.id.editText);
        mSendButton = findViewById(R.id.button2);

            //Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference =
                mFirebaseDatabase.getReference().child("messages");

        mUsername = mFirebaseAuth.getCurrentUser().getDisplayName();

        // Check User
        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() == null) {
            startActivity(MainActivity.createIntent(this));
            finish();
            return;
        }

        // Chat implementation
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chat chat = new
                        Chat(mEditText.getText().toString(), mUsername, null);

                mMessagesDatabaseReference.push().setValue(chat);

                String text = mLeftMessages.getText().toString() + "\n" +
                                mUsername + " : " +
                                mEditText.getText().toString();

//                if (count % 2 != 0) {
//                    leftMessageText = text;
//                    mLeftMessages.setText(leftMessageText);
//                } else {
//                    rightMessageText = text;
//                    mRightMessages.setText(rightMessageText);
//                }

                mRightMessages.setText(text);

                mEditText.setText(R.string.empty_string);
                count++;

//                Toast.makeText(ChatActivity.this, "" + count, Toast.LENGTH_SHORT).show();
            }
        });



        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Chat chat =
                            dataSnapshot.getValue(Chat.class);

                    // Update chat if the sender is different from current user
                    if (chat.getName() != mUsername) {
                        String text = mLeftMessages.getText().toString() + "\n" +
                                chat.getName() + " : " + chat.getText();
//                        if (count % 2 != 0) {
//                            leftMessageText = text;
//                            mLeftMessages.setText(leftMessageText);
//                        } else {
//                            rightMessageText = text;
//                            mRightMessages.setText(rightMessageText);
//                        }
                        mLeftMessages.setText(text);
                    }

                    count++;

//                    Toast.makeText(ChatActivity.this, "" + count, Toast.LENGTH_SHORT).show();
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
