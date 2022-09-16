package com.example.notesapp.arch;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.notesapp.database.AppDatabase;
import com.example.notesapp.model.NoteEntity;
import com.example.notesapp.utils.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {


    AppDatabase appDatabase;
    public LiveData<List<NoteEntity>>mNotesLiveData;
    public ArrayList<NoteEntity> noteEntities;
    Executor mExecutor = Executors.newSingleThreadExecutor();

    public static AppRepository outInstance;

    public static AppRepository getInstance(Context context) {
        return outInstance = new AppRepository(context);

    }

    private AppRepository(Context context){
        appDatabase = AppDatabase.getInstance(context);
        mNotesLiveData = getAllNotes();
        noteEntities = DataProvider.getSimpleData();
    }


    public void insertSampleData() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.notesDao().insertAll(noteEntities);
            }
        });
    }

    public LiveData<List<NoteEntity>> getAllNotes(){
        return appDatabase.notesDao().getAllNotes();
    }


    public void deleteAllData() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int i = appDatabase.notesDao().deleteAllNotes();
            }
        });
    }


    public NoteEntity loadNote(int noteId) {
        return appDatabase.notesDao().getNoteBYId(noteId);
    }

    public void insertNote(NoteEntity note) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.notesDao().insertNote(note);
            }
        });

    }

    public void deleteNote(NoteEntity note) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.notesDao().deleteNote(note);
            }
        });
    }
}
