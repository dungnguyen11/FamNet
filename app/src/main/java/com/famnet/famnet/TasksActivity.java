package com.famnet.famnet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TasksActivity extends AppCompatActivity {

    private String TAG = "TasksActivity";

    private Button testButton;
    private LinearLayout mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        mainContainer = findViewById(R.id.main_container);


    }

    private void createTaskLayout(LinearLayout container){
        //Testing
        testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "clicked");
                LinearLayout horizontalContainer = new LinearLayout(TasksActivity.this, null);
                horizontalContainer.setOrientation(LinearLayout.HORIZONTAL);
//                horizontalContainer.set

                Button takeButton = new Button(TasksActivity.this, null);
                horizontalContainer.addView(takeButton);

                LinearLayout verticalContainer = new LinearLayout(TasksActivity.this, null);
                horizontalContainer.addView(verticalContainer);
                TextView taskNameTextView = new TextView(TasksActivity.this, null);
                TextView taskRewardTextView = new TextView(TasksActivity.this, null);
                TextView taskDeadlineTextView = new TextView(TasksActivity.this, null);
                verticalContainer.addView(taskNameTextView);
                verticalContainer.addView(taskRewardTextView);
                verticalContainer.addView(taskDeadlineTextView);

            }
        });
    }
}
