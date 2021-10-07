package com.example.planner_.screens.tasks;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.example.planner_.App;
import com.example.planner_.R;
import com.example.planner_.model.NoteModel;
import com.example.planner_.model.TaskModel;
import com.example.planner_.screens.main.TaskAdapter;
import com.example.planner_.screens.note.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nhaarman.async.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Tasks extends Fragment {

    public Tasks() {
        // Required empty public constructor
    }

    int id;
    public static RecyclerView recyclerView;
    public static List<TaskModel> tasks = new ArrayList<>();
    public static List<TaskModel> tmp = new ArrayList<>();
    public ImageView doneButton;
    public TextView todoButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        final NavController navController = NavHostFragment.findNavController(this);
        final TaskAdapter taskAdapter = new TaskAdapter();
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) view.findViewById(R.id.listView);
        id = 0;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(taskAdapter);

        TaskViewModel mainViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mainViewModel.getTaskLiveData().observe(getViewLifecycleOwner(), new Observer<List<TaskModel>>() {
            @Override
            public void onChanged(List<TaskModel> tasks) {
                taskAdapter.setItems(tasks);
            }
        });

        FloatingActionButton addTask = view.findViewById(R.id.add_task);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.addTask);
            }
        });

        return view;
    }

    public void doneClick(View v) {
        final int position = recyclerView.getChildLayoutPosition((View) v.getParent());
        SwipeLayout s = (SwipeLayout) recyclerView.getChildAt(position);
        TaskModel a = tasks.get(position);
        a.done = true;
        App.getInstance().getTaskDao().update(a);
        s.close(true);
    }
}

