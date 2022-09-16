package com.example.notesapp.utils;

import com.example.notesapp.model.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DataProvider {
    private static final String SAMPLE_TEXT_1 = "myimple Note";
    private static final String SAMPLE_TEXT_2 = "abimple Note\n sith line feed";
    private static final String SAMPLE_TEXT_3 = "taimple Note\n dfjoioerjfg;jdfkjg;lldjrtoierjtl erf";

    private static Date getDate(int diffAmount){

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MILLISECOND, diffAmount);
        return calendar.getTime();
    }

    public static ArrayList<NoteEntity> getSimpleData(){
        ArrayList<NoteEntity> noteEntities = new ArrayList<>();
        noteEntities.add(new NoteEntity(getDate(1),SAMPLE_TEXT_1));
        noteEntities.add(new NoteEntity(getDate(-1),SAMPLE_TEXT_2));
        noteEntities.add(new NoteEntity(getDate(-2),SAMPLE_TEXT_3));
        return noteEntities;

    }
}
