package com.aryef.samples.sqllitenotebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by arye on 30/12/13.
 */
public class NotesDatabase extends SQLiteOpenHelper{
    static final String DATABASE_NAME= "NotesManagerSuper";
    static final int DATABASE_VERSION = 2;
    static final String NOTES_TABLE = "NOTES";
    static final String KEY_LABEL ="LABEL";
    static final String KEY_TIMESTAMP = "TIMESTAMP";
    //need to work with cursor adapter!!!
    static final String KEY_ID = "_id";

    static final String CREATE_NOTES_TABLE =
            "CREATE TABLE " + NOTES_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " + KEY_LABEL + " TEXT, " +
                    KEY_TIMESTAMP + " BIGINT )";




    private static NotesDatabase ourInstance;



    public static NotesDatabase GetInstance() {

        return ourInstance;
    }

    public static NotesDatabase GetInstance(Context context)
    {
        if( ourInstance == null)
        {
            ourInstance = new NotesDatabase(context);

        }

        return ourInstance;
    }

    private NotesDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2)
    {
        db.execSQL("DROP_ TABLE IF EXISTS " + NOTES_TABLE);
    }

    public Cursor GetCursorOnAllData()
    {
        SQLiteDatabase db = super.getReadableDatabase();

        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM "
                + NOTES_TABLE +  " ORDER BY " + KEY_TIMESTAMP + " DESC", null);
        return cursor;
    }


    public void RemoveNote (long noteId)
    {
        SQLiteDatabase db = super.getWritableDatabase();
        db.delete(NOTES_TABLE, "_id = " + noteId, null);
        db.close();

    }

    public  Note GetNote(long noteId)
    {
        Note note = null;
        SQLiteDatabase db = super.getReadableDatabase();

        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM "
                + NOTES_TABLE +  " WHERE _ID = " + noteId , null);

        if (cursor != null)

            if(cursor.moveToFirst())
            {
                do {
                    String lable = cursor.getString(cursor.getColumnIndex(KEY_LABEL));
                    long Timestamp = cursor.getLong(cursor.getColumnIndex(KEY_TIMESTAMP));

                    note = new Note(lable, Timestamp);
                    break;
                }
                while (cursor.moveToNext());

            }

        return  note;
    }
    public void AddNote(Note note)
    {
        SQLiteDatabase db = super.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_LABEL, note.getNote());
        values.put(KEY_TIMESTAMP, note.getTime());
        db.insert(NOTES_TABLE, null, values);
        db.close();

    }

    public void DeleteAll()
    {
        SQLiteDatabase db = super.getWritableDatabase();
        db.delete(NOTES_TABLE, null, null);
        db.close();

    }
}


