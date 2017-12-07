package com.famnet.famnet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.famnet.famnet.Model.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewTaskActivity extends AppCompatActivity {

    //View
    private TextView taskNameTextView;
    private TextView taskRewardTextView;
    private TextView taskDescriptionTextView;
    private TextView taskDeadlineTextView;
    private Spinner assignToSpinner;
    private Button createButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        //Initialize view
        taskNameTextView = findViewById(R.id.new_task_name_text_view);
        taskRewardTextView = findViewById(R.id.new_task_reward_text_view);
        taskDescriptionTextView = findViewById(R.id.new_task_description_text_view);
        taskDeadlineTextView = findViewById(R.id.new_task_deadline_text_view);
        assignToSpinner = findViewById(R.id.assign_to_spinner);
        createButton = findViewById(R.id.task_create_button);

        //Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tasks = database.getReference("Tasks");

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create new task with information from views, and add to the database
                Task task = new Task(taskNameTextView.getText().toString(),
                        taskDescriptionTextView.getText().toString(),
                        taskDeadlineTextView.getText().toString());
                tasks.child(task.getId().toString()).setValue(task);
                finish();
//                createButton.setVisibility(View.INVISIBLE);
            }
        });




    }


}
