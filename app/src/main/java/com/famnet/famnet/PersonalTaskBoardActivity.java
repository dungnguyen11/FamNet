package com.famnet.famnet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PersonalTaskBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_task_board);
    }
    ///CLICK ON ADD NEW TASK BUTTON
    public void onClickAddNewTask(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }
}
