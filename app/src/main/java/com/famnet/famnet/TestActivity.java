package com.famnet.famnet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestActivity extends AppCompatActivity {

    private Button accountButton;
    private Button chatButton;
    private Button newTaskButton;
    private Button personalTaskButton;
    private Button tasksButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //init view
        accountButton = findViewById(R.id.account_activity_button);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

        chatButton = findViewById(R.id.chat_activity_button);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
        newTaskButton = findViewById(R.id.new_task_activity_button);
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, NewTaskActivity.class);
                startActivity(intent);
            }
        });
        personalTaskButton = findViewById(R.id.personal_task_activity_button);
        personalTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, PersonalTasksActivity.class);
                startActivity(intent);
            }
        });
        tasksButton = findViewById(R.id.tasks_activity_button);
        tasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, TasksActivity.class);
                startActivity(intent);
            }
        });



    }
}
