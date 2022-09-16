package com.example.notesapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.notesapp.model.NoteEntity;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteEntity note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NoteEntity> noteEntities);

    @Delete()
    void deleteNote(NoteEntity note);

    @Query("SELECT * FROM notes WHERE id= :id")
    NoteEntity getNoteBYId(int id);

    @Query("SELECT * FROM notes ORDER BY date DESC ")
    LiveData<List<NoteEntity>> getAllNotes();

    @Query("DELETE FROM notes")
    int deleteAllNotes();

    @Query("SELECT COUNT(*) FROM notes")
    int getCount();

}
