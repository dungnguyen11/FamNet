package com.famnet.famnet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class PersonalTasksActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tasks);

        //Navigation bar
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.navigation_personal_task);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
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

    ///CLICK ON ADD NEW TASK BUTTON
    public void onClickAddNewTask(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }
}
