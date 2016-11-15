package com.software.fire.todo.ui.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.software.fire.todo.R;
import com.software.fire.todo.database.DBHelper;
import com.software.fire.todo.models.Todo;
import com.software.fire.todo.services.AddTodoService;
import com.software.fire.todo.utils.dateUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class AddToDoActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Todo mTodo;
    private TextView mDateView;
    private TextView mTimeView;
    private Date mDate;
    private Calendar mCalendar = Calendar.getInstance();
    private AutoCompleteTextView mDescriptionView;
    private boolean mUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDateView = (TextView) findViewById(R.id.date_tv);
        mDateView.setOnClickListener(this);
        mTimeView = (TextView) findViewById(R.id.time_tv);
        mTimeView.setOnClickListener(this);
        mDescriptionView = (AutoCompleteTextView)
                findViewById(R.id.description_view);
        mDate = new Date();
        mTodo = new Todo();

        mUpdate = false;
        if (getIntent().hasExtra(DBHelper.KEY_DESCRIPTION)) {
            mUpdate = true;

            //Getting intent data
            Intent intent = getIntent();
            String intentDescription = intent.getStringExtra(DBHelper.KEY_DESCRIPTION);
            String intentTime = intent.getStringExtra(DBHelper.KEY_TIME);
            int intentID = intent.getIntExtra(DBHelper.KEY_ID, 0);

            //Instantiating Todo Object
            mTodo.setDescription(intentDescription);
            mTodo.setTime(intentTime);
            mTodo.setId(intentID);

            mDate.setTime(Long.parseLong(intentTime));

            //Setting texts
            mDescriptionView.setText(mTodo.getDescription());
            mDateView.setText(dateUtils.getReadableDate(mTodo.getTime()));
            mTimeView.setText(dateUtils.getTime(mTodo.getTime()));

        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting description from edittext and updating/creating todo object
                mCalendar.setTime(mDate);
                String description = mDescriptionView.getText().toString();
                long time = mDate.getTime();

                mTodo.setDescription(description);
                mTodo.setTime(String.valueOf(time));

                ContentValues contentValues = new ContentValues();
                contentValues.put(DBHelper.KEY_DESCRIPTION, mTodo.getDescription());
                contentValues.put(DBHelper.KEY_TIME, mTodo.getTime());
                if(!mUpdate) {
                    AddTodoService.insertNewTodo(AddToDoActivity.this, contentValues);
                }else{
                    contentValues.put(DBHelper.KEY_ID, mTodo.getId());
                    AddTodoService.updateTodo(AddToDoActivity.this, contentValues);
                }
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_tv:
                setDate();
                break;
            case R.id.time_tv:
                setTime();
                break;
        }
    }

    private void setTime() {

        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                true
        );
        timePickerDialog.setOnTimeSetListener(this);
        timePickerDialog.show(getFragmentManager(), null);
    }

    private void setDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                AddToDoActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setMinDate(now);
        datePickerDialog.setOnDateSetListener(this);
        datePickerDialog.show(getFragmentManager(), null);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mDate.setYear(year);
        mDate.setMonth(monthOfYear);
        mDate.setDate(dayOfMonth);
        mDateView.setText(dateUtils.getReadableDate(String.valueOf(mDate.getTime())));
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        mDate.setHours(hourOfDay);
        mDate.setMinutes(minute);
        mDate.setSeconds(second);

        mTimeView.setText(hourOfDay + ":" + minute);
    }
}
