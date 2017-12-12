package com.famnet.famnet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.famnet.famnet.Model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private String TAG = "TasksActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mTaskAdapter;
    private List<Task> mTaskBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        //Recycler View
        mRecyclerView = findViewById(R.id.recycler_view); //Initialize
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); //set LayoutManager

        //TODO: Read tasks from Firebase to update real tasks
//        List<Task> tasksBoard;
//        mTaskBoard.add(new Task("task1Name", "task1Des", "task1Reward", "task1Deadline"));

        //Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tasksRef = database.getReference("Tasks");
        mTaskBoard = new ArrayList<>();

        tasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                GenericTypeIndicator<ArrayList<Task>> dataType =
//                        new GenericTypeIndicator<ArrayList<Task>>() {};
//                ArrayList<Task> tasks = dataSnapshot.getValue(dataType);
                List<Task> taskList = new ArrayList<>();

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Task task = postSnapShot.getValue(Task.class);
                    taskList.add(task);
                    Log.d("Get data", task.getId());
                }

                mTaskBoard = taskList;
                Log.d("mTaskBoard", String.valueOf(mTaskBoard.isEmpty()));
                Log.d("mTaskBoard value", mTaskBoard.get(0).getId());

                updateUI(mTaskBoard);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Failed to read value
                Toast.makeText(TasksActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();

            }
        });

//        Log.d("mTaskBoard value check", String.valueOf(mTaskBoard.isEmpty()));
//        Log.d("mTaskBoard value", mTaskBoard.get(0).getId());

//        if (mTaskBoard != null && !mTaskBoard.isEmpty()) {
//            updateUI(mTaskBoard);
//        } else {
//            Toast.makeText(this, "Failed to read data (outside)", Toast.LENGTH_SHORT).show();
//        }

    }

    private void updateUI(List<Task> tasks) {
        mTaskAdapter = new TaskAdapter(tasks);
        mRecyclerView.setAdapter(mTaskAdapter);
    }


    //ViewHolder class for RecyclerView
    public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Task mTask;
        private TextView mTaskNameTextView;
        private TextView mTaskRewardTextView;
        private TextView mTaskDeadlineTextView;
        //TODO: implement when user press Take button
        private Button mTaskTakeButton;

        public TaskHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTaskNameTextView = itemView.findViewById(R.id.task_name_text_view);
            mTaskRewardTextView = itemView.findViewById(R.id.task_reward_text_view);
            mTaskDeadlineTextView = itemView.findViewById(R.id.task_deadline_text_view);
            mTaskTakeButton = itemView.findViewById(R.id.task_take_button);
        }

        public void bind(Task task) {
            mTask = task;
            mTaskNameTextView.setText(mTask.getName());
            mTaskRewardTextView.setText(mTask.getReward());
            mTaskDeadlineTextView.setText(mTask.getDeadline());
        }

        //TODO:implment onClick()
        @Override
        public void onClick(View v) {

        }
    }

    //Adapter class for RecyclerView
    public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = View.inflate(TasksActivity.this, R.layout.list_item_task, parent);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }



}
