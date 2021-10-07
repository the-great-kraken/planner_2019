package com.example.planner_.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.planner_.model.ListModel;

import java.util.List;

@Dao
public interface ListDao {

/*    @Query("SELECT * FROM ListModel")
    List<ListModel> getAll();

    @Query("SELECT * FROM NoteModel")
    LiveData<List<ListModel>> getAllLiveData();

    @Query("SELECT * FROM ListModel WHERE id IN (:listIds)")
    List<ListModel> loadAllByIds(int[] listIds);

    @Query("SELECT * FROM NoteModel WHERE id = :id LIMIT 1")
    ListModel findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ListModel note);

    @Update
    void update(ListModel note);

    @Delete
    void delete(ListModel note);*/

}
