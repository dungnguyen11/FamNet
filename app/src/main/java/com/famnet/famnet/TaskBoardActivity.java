package com.famnet.famnet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskBoardActivity extends AppCompatActivity {

    private String TAG = "TaskBoardActivity";

    private Button testButton;
    private LinearLayout mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_board);

        mainContainer = findViewById(R.id.main_container);


    }

    private void createTaskLayout(LinearLayout container){
        //Testing
        testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "clicked");
                LinearLayout subContainer = new LinearLayout(TaskBoardActivity.this, null);
                TextView newTextView = new TextView(TaskBoardActivity.this, null);
                newTextView.setText("new Text defined");
                subContainer.addView(newTextView);
                mainContainer.addView(subContainer);
            }
        });
    }
}
