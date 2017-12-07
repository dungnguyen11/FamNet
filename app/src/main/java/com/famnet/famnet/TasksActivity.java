package com.famnet.famnet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.famnet.famnet.Model.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private String TAG = "TasksActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mTaskAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        //Recycler View
        mRecyclerView = findViewById(R.id.recycler_view); //Initialize
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); //set LayoutManager

        //TODO: Read tasks from Firebase to update real tasks
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("task1Name", "task1Des", "task1Deadline"));
        tasks.add(new Task("task2Name", "task2Des", "task2Deadline"));

        updateUI(tasks);


    }

    private void updateUI(List<Task> tasks) {
        mTaskAdapter = new TaskAdapter(tasks);
        mRecyclerView.setAdapter(mTaskAdapter);
    }


    //ViewHolder class for RecyclerView
    public class TaskHolder extends RecyclerView.ViewHolder {
        private Task mTask;
        private TextView mTaskNameTextView;
        private TextView mTaskRewardTextView;
        private TextView mTaskDeadlineTextView;
        //TODO: implement when user press Take button
        private Button mTaskTakeButton;

        public TaskHolder(View itemView) {
            super(itemView);
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
