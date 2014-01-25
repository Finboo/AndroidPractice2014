package com.aryef.samples.mynotes;

import android.content.Context;
import android.content.res.Resources;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by arye on 23/12/13.
 */
public class CustomNotesAdapter extends ArrayAdapter {

    Context mycontext;
    LayoutInflater myInflater;

    ArrayList<Note> mdata;


    public CustomNotesAdapter(Context context, ArrayList<Note> data){
        super(context, 1, data);
        mdata= data;
        mycontext=context;
        myInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); {


};
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Object getItem(int i) {
        return mdata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int indexer, View convertview, ViewGroup parent) {

        View view = null;

        Resources res = mycontext.getResources();

        try
        {
            int layoutid = R.layout.note_layout;

             //   view = myInflater.inflate(res.getLayout(layoutid), parent, false);
            view = myInflater.inflate(R.layout.note_layout, parent, false);

        }catch (Exception e)
        {
            EventLog.writeEvent(3121, e.getMessage().toString());
        }
        if(view !=null)
        {
            if(view.findViewById(R.id.mynote) != null) {
                ((TextView) view.findViewById(R.id.mynote)).setText(mdata.get(indexer).NoteString());
            }

            if (view.findViewById(R.id.mytimestamp) !=null)
            {
                ((TextView) view.findViewById(R.id.mytimestamp)).setText(mdata.get(indexer).TimeStamp().toString());
            }
        }

        return view;
    }

}
