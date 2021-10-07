package com.example.planner_.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.planner_.model.ListModel;
import com.example.planner_.model.NoteModel;
import com.example.planner_.model.TaskModel;

@Database(entities = {NoteModel.class, TaskModel.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    public abstract ListDao listDao();
    public abstract TaskDao taskDao();

}


