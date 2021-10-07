package com.example.planner_.screens.tasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.planner_.App;
import com.example.planner_.model.TaskModel;

import java.util.List;

public class TaskViewModel extends ViewModel {

    private LiveData<List<TaskModel>> taskLiveData = App.getInstance().getTaskDao().getAllLiveData();

    public LiveData<List<TaskModel>> getTaskLiveData() {
        return taskLiveData;
    }

}
