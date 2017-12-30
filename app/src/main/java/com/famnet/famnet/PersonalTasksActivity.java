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
    private ImageView mNewTaskImageView;
    private RecyclerView mPersonalRecyclerView;
    private PersonalTaskAdapter mPersonalTaskAdapter;


    //Firebase
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mUsersRef;
    DatabaseReference mTaskListRef;
    FirebaseUser mCurrentUser;

    // Properties
    private List<Task> mPersonalTaskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tasks);

        // Firebase init
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mUsersRef = mDatabase.getReference("Users");
        mTaskListRef = mUsersRef.child(mCurrentUser.getUid()).child("tasks");

        // Recycler View init
        mPersonalRecyclerView = findViewById(R.id.personal_tasks_recyclerView);
        mPersonalRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNewTaskImageView = findViewById(R.id.add_new_task_ImageView);
        mNewTaskImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalTasksActivity.this, NewTaskActivity.class);
                startActivity(intent);
            }
        });


        // Properties
        mPersonalTaskList = new ArrayList<>();

        // Check if user already log in, if not,
        // change user to log in page (MainActivity)
        if (mAuth.getCurrentUser() == null) {
            startActivity(MainActivity.createIntent(this));
            finish();
        }

        // Add listener to Personal Task List to update task when task is taken, or done
        mTaskListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Task> personalTaskList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Task task = postSnapshot.getValue(Task.class);
                    personalTaskList.add(task);
                }

                // Displaying personal task list
                mPersonalTaskList = personalTaskList;
                updatePersonalTask(mPersonalTaskList);
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

    // ViewHolder class for RecyclerView
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
            //TODO: Implement checkbox
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
        }
    }

    // Adapter class for RecyclerView
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
}
