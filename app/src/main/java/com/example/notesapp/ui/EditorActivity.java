package com.example.notesapp.ui;

import static com.example.notesapp.utils.Constants.EDITING_KEY;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.notesapp.R;
import com.example.notesapp.utils.Constants;
import com.example.notesapp.arch.EditorActivityViewModel;
import com.example.notesapp.model.NoteEntity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesapp.databinding.ActivityEditorBinding;

import java.util.Objects;

public class EditorActivity extends AppCompatActivity {

    private boolean isEditing;
    private ActivityEditorBinding binding;
    private EditorActivityViewModel mViewModel;
    private boolean isEditorActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_check_24);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState != null){
            isEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }
        initViewModel();

        if (getIntent().getExtras() == null) {
            setTitle("Add New Note");
            isEditorActivity = false;
        } else {
            setTitle("Edit Note");
            isEditorActivity = true;
            if (getIntent().hasExtra(Constants.NOTE_ID_KEY)) {
                int noteId = getIntent().getIntExtra(Constants.NOTE_ID_KEY, 0);
                mViewModel.loadNote(noteId);
            }

        }


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putBoolean(EDITING_KEY,true);
        super.onSaveInstanceState(outState);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(EditorActivityViewModel.class);
        Observer<NoteEntity> observer = new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity note) {

                if (note != null && !isEditing) {
                    binding.etNoteText.setText(note.getText());
                }
            }
        };
        mViewModel.mLiveNote.observe(EditorActivity.this, observer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_activity_menu,menu);
        MenuItem deleteItem = menu.findItem(R.id.delete_note);
        deleteItem.setVisible(isEditorActivity);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                saveAndExit();
                break;
            }
            case R.id.delete_note:{
                deleteNote();
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {
        mViewModel.deleteNote(mViewModel.mLiveNote.getValue());
    }

    private void saveAndExit() {
        mViewModel.editAndExit(binding.etNoteText.getText().toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveAndExit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}