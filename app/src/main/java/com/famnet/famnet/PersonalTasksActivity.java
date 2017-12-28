package com.famnet.famnet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famnet.famnet.Model.Task;
import com.famnet.famnet.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PersonalTasksActivity extends AppCompatActivity {

    // Constant
    private static final String TAG = "PersonalTaskActivity";

    // View
    private ImageView newTaskImageView;
    private RecyclerView mPersonalRecyclerView;
    private PersonalTaskAdapter mPersonalTaskAdapter;


    //Firebase
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mUsersPreference;
    DatabaseReference mTaskListPreference;
    FirebaseUser mCurrentUser;

    // Properties
    private User mUser;
    private List<Task> mPersonalTaskBoard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tasks);

        // Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mUsersPreference = mDatabase.getReference("Users");
        mTaskListPreference = mUsersPreference.child(mCurrentUser.getUid()).child("tasks");
        Log.d(TAG, "tasks: " + mTaskListPreference.toString());

        // View
        mPersonalRecyclerView = findViewById(R.id.personal_tasks_recyclerView);
        mPersonalRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Properties
        mPersonalTaskBoard = new ArrayList<>();

        //Check User
        if (mFirebaseAuth.getCurrentUser() == null) {
            startActivity(MainActivity.createIntent(this));
            finish();
            return;
        }

        mTaskListPreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Task> personalTaskList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Task task = postSnapshot.getValue(Task.class);
                    personalTaskList.add(task);
                    Log.d("PersonalTaskActivity", "Getting data: " + task.getName());
                }

                // Displaying personal task list
                Log.d(TAG, "personal task list: " + personalTaskList.toString());
                mPersonalTaskBoard = personalTaskList;
                Log.d(TAG, "m personal task list: " + mPersonalTaskBoard.toString());
                updatePersonalTask(mPersonalTaskBoard);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PersonalTasksActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
            }
        });


        //Navigation bar
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.navigation_personal_task);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_chat:
                        Intent intent1 = new Intent(PersonalTasksActivity.this, ChatActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_tasks:
                        Intent intent2 = new Intent(PersonalTasksActivity.this, TasksActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_personal_task:
                        break;
                    case R.id.navigation_account:
                        Intent intent4 = new Intent(PersonalTasksActivity.this, AccountActivity.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }

    private void updatePersonalTask(List<Task> tasks) {
        mPersonalTaskAdapter = new PersonalTaskAdapter(tasks);
        mPersonalRecyclerView.setAdapter(mPersonalTaskAdapter);
        Log.d(TAG, "In update function");
    }

    public class PersonalTaskHolder extends RecyclerView.ViewHolder {

        private Task mTask;
        private TextView mTaskName;
        private TextView mTaskReward;
        private TextView mTaskDeadline;
        private CheckBox mCheckBox;


        public PersonalTaskHolder(View itemView) {
            super(itemView);
            mTaskName = itemView.findViewById(R.id.personal_task_name_textView);
            mTaskReward = itemView.findViewById(R.id.personal_task_reward_textView);
            mTaskDeadline = itemView.findViewById(R.id.personal_task_deadline_textView);
            mCheckBox = itemView.findViewById(R.id.personal_task_checkBox);
        }

        public void bind(Task task) {
            mTask = task;
            mTaskName.setText(mTask.getName());
            mTaskReward.setText("Reward: " + mTask.getReward());
            mTaskDeadline.setText("Description: " + mTask.getDeadline());
            //TODO: Implement this
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
        }
    }

    public class PersonalTaskAdapter extends RecyclerView.Adapter<PersonalTaskHolder> {
        private List<Task> mTasks;

        public PersonalTaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @Override
        public PersonalTaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_task_row, parent, false);
            return new PersonalTaskHolder(view);
        }

        @Override
        public void onBindViewHolder(PersonalTaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }


    ///CLICK ON ADD NEW TASK BUTTON
    public void onClickAddNewTask(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }
}
