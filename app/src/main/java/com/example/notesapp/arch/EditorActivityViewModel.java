package com.example.notesapp.arch;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.notesapp.model.NoteEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorActivityViewModel extends AndroidViewModel {

    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public MutableLiveData<NoteEntity> mLiveNote = new MutableLiveData<>();
    private AppRepository appRepository;

    public EditorActivityViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getInstance(application.getApplicationContext());
    }


    public void loadNote(int noteId) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity noteEntity = appRepository.loadNote(noteId);
                mLiveNote.postValue(noteEntity);
            }
        });
    }

    public void editAndExit(String text) {
        NoteEntity note = mLiveNote.getValue();
        if (note == null) {
            if (TextUtils.isEmpty(text.trim())) {
                return;
            } else {
                note = new NoteEntity(new Date(), text.trim());
            }
        } else {

            if (TextUtils.isEmpty(text.trim())) {
                return;
            }else {
                note.setText(text.trim());
                note.setDate(new Date());
            }

        }
        Log.d("mytag", "editAndExit: ");
        appRepository.insertNote(note);
    }

    public void deleteNote(NoteEntity note) {
        if (note!= null){
            appRepository.deleteNote(note);
        }
    }
}
