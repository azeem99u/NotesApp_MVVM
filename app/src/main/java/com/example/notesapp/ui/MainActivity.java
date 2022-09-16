package com.example.notesapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.notesapp.NoteListAdapter;
import com.example.notesapp.R;
import com.example.notesapp.arch.ListActivityViewModel;
import com.example.notesapp.model.NoteEntity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.notesapp.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    private ActivityMainBinding binding;
    private NoteListAdapter noteListAdapter;
    private ArrayList<NoteEntity> noteEntitiesList = new ArrayList<>();
    private ListActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Notes");
        initViewModel();
        initRecyclerView();
        recyclerView = findViewById(R.id.noteListRecyclerView);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EditorActivity.class));

            }
        });
    }

    private void initRecyclerView() {

        noteListAdapter= new NoteListAdapter(this,noteEntitiesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.noteListRecyclerView.hasFixedSize();
        binding.noteListRecyclerView.setLayoutManager(layoutManager);
        binding.noteListRecyclerView.setAdapter(noteListAdapter);



        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                binding.noteListRecyclerView.getContext(),
                layoutManager.getOrientation());
        binding.noteListRecyclerView.addItemDecoration(dividerItemDecoration);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                NoteEntity note = noteListAdapter.getNoteByItsPosition(viewHolder.getAdapterPosition());
                deleteNote(note);
                Snackbar.make(viewHolder.itemView, "Note Deleted successfully!", Snackbar.LENGTH_SHORT).show();
            }
        });

        itemTouchHelper.attachToRecyclerView(binding.noteListRecyclerView);
        divideItemDecoration();
    }

    public void divideItemDecoration() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.noteListRecyclerView.setLayoutManager(layoutManager);

    }

    private void deleteNote(NoteEntity note) {
        mViewModel.deleteNote(note);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(ListActivityViewModel.class);
        Observer<List<NoteEntity>> observer = new Observer<List<NoteEntity>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {

                noteEntitiesList.clear();
                noteEntitiesList.addAll(noteEntities);
                noteListAdapter.notifyDataSetChanged();

                if (noteEntities.isEmpty()){
                    binding.imageView.setVisibility(View.VISIBLE);
                    binding.noteListRecyclerView.setVisibility(View.GONE);
                }else {
                    binding.noteListRecyclerView.setVisibility(View.VISIBLE);
                    binding.imageView.setVisibility(View.GONE);
                }
            }
        };

        mViewModel.mListLiveData.observe(MainActivity.this, observer);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_sample_data: {
                insertSampleData();
                return true;
            }

            case R.id.delete_all_data: {
                deleteAllData();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllData() {
        mViewModel.deleteAllData();
    }

    private void insertSampleData() {
        mViewModel.insertSampleData();
    }
}