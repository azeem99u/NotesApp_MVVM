package com.example.notesapp.arch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesapp.model.NoteEntity;

import java.util.List;

public class ListActivityViewModel extends AndroidViewModel {


    public LiveData<List<NoteEntity>> mListLiveData;

    private AppRepository mAppRepository;
    public ListActivityViewModel(@NonNull Application application) {
        super(application);
        mAppRepository =AppRepository.getInstance(application.getApplicationContext());
        mListLiveData = mAppRepository.mNotesLiveData;
    }

    public void insertSampleData() {
        mAppRepository.insertSampleData();
    }

    public void deleteAllData() {
        mAppRepository.deleteAllData();
    }

    public void deleteNote(NoteEntity note) {
        mAppRepository.deleteNote(note);
    }
}
