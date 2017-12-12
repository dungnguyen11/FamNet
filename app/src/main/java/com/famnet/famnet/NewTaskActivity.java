package com.famnet.famnet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.famnet.famnet.Model.Task;
import com.famnet.famnet.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    //View
    private TextView taskNameTextView;
    private TextView taskRewardTextView;
    private TextView taskDescriptionTextView;
    private TextView taskDeadlineTextView;
    private Spinner assignToSpinner;
    private Button createButton;
    private EditText Output;
    private ImageView changeDate;

    private int year;
    private int month;
    private int day;

    static final int DATE_PICKER_ID = 1111;


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
                finish();
//                createButton.setVisibility(View.INVISIBLE);
            }
        });

        /// THE BEGIN OF DATE INPUT FOR DEADLINE
        Output = (EditText) findViewById(R.id.new_task_deadline_text_view);
        changeDate = (ImageView) findViewById(R.id.CalendarIcon);

        // Get current date by calender

        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        // Show current date

        Output.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        // Button listener to show date picker dialog

        changeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);

            }

        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            Output.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

    /// THE END OF DATE INPUT FOR DEADLINE
}

