package com.example.planner_.screens.tasks;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.planner_.App;
import com.example.planner_.R;
import com.example.planner_.model.TaskModel;
import com.example.planner_.screens.main.MainActivity;
import com.nhaarman.async.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskEditor extends Fragment {

    public TaskEditor() {
        // Required empty public constructor
    }

    private Calendar calendar;
    private TextView dateView, timeView;
    private int year, month, day, hour, minute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        // Inflate the layout for this fragment
        dateView = (TextView) view.findViewById(R.id.date);
        timeView = (TextView) view.findViewById(R.id.time);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        try {
            showDate(year, month + 1, day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        showTime(hour, minute);

        final Button button = (Button) view.findViewById(R.id.save_task);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    save(view);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        final Button button1 = (Button) view.findViewById(R.id.time);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    setTime(view);
            }
        });

        final Button button2 = (Button) view.findViewById(R.id.date);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    setDate(view);
            }
        });

        return view;
    }

    @SuppressWarnings("deprecation")

    public void setDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                myDateListener, year, month, day);
        datePickerDialog.show();
    }

    public void setTime(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                myTimeListener, hour, minute, true);
        timePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    try {
                        showDate(arg1, arg2 + 1, arg3);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            };

    private TimePickerDialog.OnTimeSetListener myTimeListener = new
            TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker arg0,
                                      int arg1, int arg2) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showTime(arg1, arg2);
                }
            };


    private void showDate(int year, int month, int day) throws ParseException {
        String d = (String.format("%02d", day) + "/" + String.format("%02d", month) + "/" + year);
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date MyDate = newDateFormat.parse(d);
        newDateFormat.applyPattern("EE d MMM yyyy");
        String MySDate = newDateFormat.format(MyDate);
        dateView.setText(MySDate);
    }

    private void showTime(int hour, int minute) {
        timeView.setText(String.format("%02d", hour) + ":" + String.format("%02d", minute));
    }

    public void save(View view) throws ParseException {
        TaskModel newTask = new TaskModel();

        Date current = new Date();
        String title = ((TextView) view.findViewById(R.id.title)).getText().toString();
        String txt = ((TextView) view.findViewById(R.id.txt)).getText().toString().replace('<', ' ');
        if (title.equals("") || txt.equals(""))
            Toast.makeText(getContext(), "Error title and description cannot be empty !", Toast.LENGTH_SHORT).show();
        else {
            String d = ((TextView) view.findViewById(R.id.date)).getText().toString() + " " + ((TextView) view.findViewById(R.id.time)).getText().toString();
            SimpleDateFormat newDateFormat = new SimpleDateFormat("EE d MMM yyyy k:m");
            Date date = newDateFormat.parse(d);
            if (date.after(current)) {
                newTask.title = title;
                newTask.txt = txt;
                newTask.date = date.getTime();
                //newTask.done = false;

                App.getInstance().getTaskDao().insert(newTask);


                Date f = new Date();
                int delay = (int) (date.getTime() - f.getTime());
                if (delay > 0) {

                    Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                    intent.putExtra("txt", txt);
                    intent.putExtra("title", title);
                    intent.putExtra("delay", delay);
                    intent.putExtra("done", "false");
                    getActivity().startActivity(intent);

                }

            } else
                Toast.makeText(getContext(), "Error you can't enter a date that is already passed !", Toast.LENGTH_SHORT).show();
        }
    }


}
