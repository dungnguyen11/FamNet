package com.famnet.famnet;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.famnet.famnet.Model.Task;
import com.famnet.famnet.Model.User;
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
        //TODO: implement spinner for assign to
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
                        taskRewardTextView.getText().toString(),
                        taskDeadlineTextView.getText().toString(),
                        (User) assignToSpinner.getSelectedItem());
                tasks.child(task.getId().toString()).setValue(task);

                Intent intent = new Intent();
                PendingIntent pendingIntent = PendingIntent.getActivity(NewTaskActivity.this,0, intent, 0);
                Notification noti = new Notification.Builder(NewTaskActivity.this)
                    .setTicker("New Task")
                        .setContentTitle("New Task Created")
                        .setContentText("Someone just created a new task. Check it now !")
                        .setSmallIcon(R.drawable.notification)
                        .setContentIntent(pendingIntent).getNotification();

                noti.flags = Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0,noti);


//                createButton.setVisibility(View.INVISIBLE);
            }
        });



    }


}
