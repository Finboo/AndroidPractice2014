package com.aryef.samples.sqllitenotebook;

import java.lang.reflect.AnnotatedElement;
import java.util.Calendar;

/**
 * Created by arye on 23/12/13.
 */
public class Note {

    private String f_note;
    private Long f_timestamp;
    private int id;

    public Note(String note, Long timestamp)
    {
        f_note = note;
        f_timestamp= timestamp;

    }

    public Note(String note)
    {
       f_note = note;
        f_timestamp= System.currentTimeMillis();

    }

    public String getNote() {
        return f_note;
    }


    public Long getTime() {
        return f_timestamp;
    }




}
