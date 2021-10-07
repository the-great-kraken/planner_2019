package com.example.planner_.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.planner_.model.TaskModel;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM TaskModel")
    List<TaskModel> getAll();

    @Query("SELECT * FROM TaskModel")
    LiveData<List<TaskModel>> getAllLiveData();

    @Query("SELECT * FROM TaskModel WHERE id IN (:taskIds)")
    List<TaskModel> loadAllByIds(int[] taskIds);

    @Query("SELECT * FROM TaskModel WHERE id = :id LIMIT 1")
    TaskModel findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskModel task);

    @Update
    void update(TaskModel task);

    @Delete
    void delete(TaskModel task);
}
