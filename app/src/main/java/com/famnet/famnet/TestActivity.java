package com.famnet.famnet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.BottomNavigationView;

import com.famnet.famnet.Model.Task;

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

        //Navigation bar
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.navigation_chat);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_chat:
                        Intent intent1 = new Intent(TestActivity.this, ChatActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_tasks:
                        Intent intent2 = new Intent(TestActivity.this, TasksActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_personal_task:
                        Intent intent3 = new Intent(TestActivity.this, PersonalTasksActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_account:
                        Intent intent4 = new Intent(TestActivity.this, AccountActivity.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });


    }
}
