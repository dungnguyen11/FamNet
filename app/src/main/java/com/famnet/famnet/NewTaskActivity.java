package com.famnet.famnet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

// THE BEGIN OF DATE INPUT FOR DEADLINE

    private TextView Output;
    private ImageView changeDate;

    private int year;
    private int month;
    private int day;

    static final int DATE_PICKER_ID = 1111;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        Output = (TextView) findViewById(R.id.deadlineinput);
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
