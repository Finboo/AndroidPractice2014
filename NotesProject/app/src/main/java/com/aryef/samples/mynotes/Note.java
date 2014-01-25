package com.aryef.samples.mynotes;

import java.lang.reflect.AnnotatedElement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by arye on 23/12/13.
 */
public class Note {

    private String f_note;
    private Calendar f_timestamp;


    public Note(String note)
    {
       f_note = note;
        f_timestamp= Calendar.getInstance();

    }

    public String NoteString() {
        return f_note;
    }


    public String TimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(f_timestamp.getTimeInMillis());
        return time;
    }


}
