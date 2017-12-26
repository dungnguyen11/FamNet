package com.famnet.famnet;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.famnet.famnet.Model.Message;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    // Constant
    private static final String TAG = "ChatActivity-Debug";

    // Views
    private EditText mEditText;
    private Button mSendButton;
    private ImageButton mPhotoPickerButton;
    private RecyclerView mRecyclerView;
    private MessageAdapter mMessageAdapter;

    //PhotoPicker
    private static final int RC_PHOTO_PICKER = 1;

    // Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrentUser;
    private ChildEventListener mChildEventListener;

    //Photo Storage
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosReference;

    // Properties
    private String mUsername = "";
    private List<Message> mMessages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Views
        mEditText = findViewById(R.id.new_chat_text);
        mSendButton = findViewById(R.id.send_button);
        mPhotoPickerButton = findViewById(R.id.photoPickerButton);

        //Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesReference = mFirebaseDatabase.getReference("Messages");
        mCurrentUser = mFirebaseAuth.getCurrentUser();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mChatPhotosReference = mFirebaseStorage.getReference().child("chat_photos");
        // Properties
        if (mCurrentUser.getDisplayName() != null) {
            mUsername = mCurrentUser.getDisplayName();
        }

        mMessages = new ArrayList<>();


        // Check User
        if (mFirebaseAuth.getCurrentUser() == null) {
            startActivity(MainActivity.createIntent(this));
            finish();
            return;
        }

        //Recycler View
        mRecyclerView = findViewById(R.id.chat_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //TODO: Still having bug when loading Message
        mMessagesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Log.d(TAG, "inside dataChange");
                    List<Message> tempMessages = new ArrayList<>();

                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Message message = postSnapShot.getValue(Message.class);
                        Log.d(TAG, "load Message: " + message.getText());
                        tempMessages.add(message);
                    }

                    mMessages = tempMessages;

                    updateMessages(mMessages);
                    // Notification
                    Intent intent = new Intent();
                    PendingIntent pendingIntent = PendingIntent.getActivity(ChatActivity.this,0, intent, 0);
                    Notification noti = new Notification.Builder(ChatActivity.this)
                            .setTicker("Famnet - New Message in Family ChatBoard")
                            .setContentTitle("Famnet - New Message in Chat Board")
                            .setContentText("Someone in your family just sent a new message in Chat Board. Check it now !")
                            .setSmallIcon(R.drawable.notification)
                            .setContentIntent(pendingIntent).getNotification();


                    noti.flags = Notification.FLAG_AUTO_CANCEL;
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0,noti);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
            }
        });



        // Message implementation
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEditText.getText().toString().equals("")) { // Message cannot be empty
                    Message message = new Message(mEditText.getText().toString(), mUsername, null);
                    mMessagesReference.push().setValue(message); // The messages will update because of valueListener of messageReference

                    // Delete the sent message
                    mEditText.setText("");
//                Toast.makeText(ChatActivity.this, "" + count, Toast.LENGTH_SHORT).show();
                    Toast.makeText(ChatActivity.this,
                            "Your message has been sent !", Toast.LENGTH_LONG).show();
                }
            }
        });

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Complete action using"), RC_PHOTO_PICKER);
            }
        });


//        if (mChildEventListener == null) {
//            mChildEventListener = new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    Message message = dataSnapshot.getValue(Message.class);
//
//                    // Update chat if the sender is different from current user
//                    if (!message.getSender().equals(mUsername)) {
//                        String text = mLeftMessages.getText().toString() + "\n" +
//                                chat.getSender() + " : " + chat.getText();
//                        mLeftMessages.setText(text);
//                    }
//
//
////                    Toast.makeText(ChatActivity.this, "" + count, Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
//                @Override
//                public void onChildRemoved(DataSnapshot dataSnapshot) {}
//                @Override
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
//                @Override
//                public void onCancelled(DatabaseError databaseError) {}
//            };
//        }
//
//        mMessagesReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

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


    // MessageHolder for Recycler View

    public class MessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Message mMessage;
        private TextView mLeftChat;
        private TextView mLeftSender;
//        private TextView mRightChat;
//        private TextView mRightSender;

        public MessageHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mLeftChat = itemView.findViewById(R.id.chat_left_textView);
            mLeftSender = itemView.findViewById(R.id.chat_sender_left_textView);
//            mRightChat = itemView.findViewById(R.id.chat_right_textView);
//            mRightSender = itemView.findViewById(R.id.chat_sender_right_textView);
        }

        public void bind(Message message) {
            mMessage = message;
            mLeftChat.setText(mMessage.getText());
            mLeftSender.setText(mMessage.getSender());
        }

        @Override
        public void onClick(View v) {

        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            //get the reference to stored file at database
            StorageReference photoReference = mChatPhotosReference.child(imageUri.getLastPathSegment());

            //upload file to firebase
            photoReference.putFile(imageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Message message = new Message(null, mUsername, downloadUrl.toString());
                    mMessagesReference.push().setValue(message);
                    Toast.makeText(ChatActivity.this,
                            "Your Image Sent", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    // ChatAdapter for Recycler View

    public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

        List<Message> mMessages;

        public MessageAdapter(List<Message> messages) {
            mMessages = messages;
        }

        @Override
        public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_message, parent, false);
            return new MessageHolder(view);
        }

        @Override
        public void onBindViewHolder(MessageHolder holder, int position) {
            Message message = mMessages.get(position);
            holder.bind(message);
        }

        @Override
        public int getItemCount() {
            Log.d("getItemCount", "Item count: " + mMessages.size());
            return mMessages.size();
        }
    }

    private void updateMessages(List<Message> messages){
        mMessageAdapter = new MessageAdapter(messages);
        mRecyclerView.setAdapter(mMessageAdapter);
    }


}
