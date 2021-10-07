package com.example.planner_.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.planner_.model.NoteModel;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM NoteModel")
    List<NoteModel> getAll();

    @Query("SELECT * FROM NoteModel")
    LiveData<List<NoteModel>> getAllLiveData();

    @Query("SELECT * FROM NoteModel WHERE id IN (:noteIds)")
    List<NoteModel> loadAllByIds(int[] noteIds);

    @Query("SELECT * FROM NoteModel WHERE id = :id LIMIT 1")
    NoteModel findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteModel note);

    @Query("UPDATE NoteModel SET text = :text, timestamp = :time WHERE id = :tid")
    void update(int tid, String text, long time);

    @Update
    void update(NoteModel note);

    @Delete
    void delete(NoteModel note);

}