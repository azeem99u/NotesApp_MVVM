package com.example.notesapp;

import static com.example.notesapp.utils.Constants.NOTE_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.model.NoteEntity;
import com.example.notesapp.ui.EditorActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<NoteEntity> noteEntities;

    public NoteListAdapter(Context mContext, ArrayList<NoteEntity> noteEntities) {
        this.mContext = mContext;
        this.noteEntities = noteEntities;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.note_list_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NoteEntity noteEntity = noteEntities.get(position);
        holder.text.setText(noteEntity.getText());
        holder.fabEdit.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, EditorActivity.class).putExtra(NOTE_ID_KEY,noteEntity.getId()));
        });
    }

    @Override
    public int getItemCount() {
        return noteEntities.size();
    }

    public NoteEntity getNoteByItsPosition(int position){
        NoteEntity note = noteEntities.get(position);
        return note;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        FloatingActionButton fabEdit;
        TextView text;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fabEdit = itemView.findViewById(R.id.fabEdit);
            text = itemView.findViewById(R.id.title_text);
        }
    }




}
