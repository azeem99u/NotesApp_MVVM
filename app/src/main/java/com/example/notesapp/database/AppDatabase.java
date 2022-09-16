package com.example.notesapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.notesapp.model.NoteEntity;

@Database(entities = {NoteEntity.class},version = 2)
@TypeConverters(DateConverter.class)
abstract public class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "notesdatabase.db";

    public static volatile AppDatabase instance;
    public static Object LOCK = new Object();
    public abstract NotesDao notesDao();

    public static AppDatabase getInstance(Context context){
        if (instance == null) {
            synchronized (LOCK){
                if (instance ==null){
                    instance = Room.
                            databaseBuilder(context.getApplicationContext(),AppDatabase.class,DATABASE_NAME).
                            build();
                }
            }
        }
        return instance;
    }

}
