package com.example.planner_;

import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.planner_.data.AppDatabase;
import com.example.planner_.data.ListDao;
import com.example.planner_.data.NoteDao;
import com.example.planner_.data.TaskDao;
import com.example.planner_.model.TaskModel;

import java.util.List;

public class App extends Application {

    private AppDatabase database;
    private NoteDao noteDao;
    private ListDao listDao;
    private TaskDao taskDao;

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "app-db-name")
                .allowMainThreadQueries()
                .build();

        database = Room.databaseBuilder(this.getApplicationContext(),
                AppDatabase.class, "Sample.db")
                .fallbackToDestructiveMigration()
                .build();

        database =  Room.databaseBuilder(this, AppDatabase.class, "MyDatabase")
                .allowMainThreadQueries().build();

        noteDao = database.noteDao();
        listDao = database.listDao();
        taskDao = database.taskDao();
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public TaskDao getTaskDao() { return taskDao; }

    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    public ListDao getListDao() { return listDao; }

    public void setListDao(ListDao listDao) { this.listDao = listDao; }

}
