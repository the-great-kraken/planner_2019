package com.example.planner_.screens.note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.planner_.App;
import com.example.planner_.model.NoteModel;

import java.util.List;

public class NoteViewModel extends ViewModel {
    private LiveData<List<NoteModel>> noteLiveData = App.getInstance().getNoteDao().getAllLiveData();

    public LiveData<List<NoteModel>> getNoteLiveData() {
        return noteLiveData;
    }
}
