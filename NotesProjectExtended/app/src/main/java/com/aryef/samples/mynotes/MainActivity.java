package com.aryef.samples.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    EditText edittext;
    ListView listView;

    final ArrayList<Note> toDoList = new ArrayList<Note>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittext = (EditText) findViewById(R.id.myeditText);

        listView =(ListView) findViewById(R.id.mylistView);

        //Controller
        final CustomNotesAdapter myarrayAdapter =
                new CustomNotesAdapter(getApplicationContext(), toDoList);

       // toDoList.add(0, new Note("start"));
        //bind list view to adapter:

        listView.setAdapter(myarrayAdapter);

        edittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyEvent.getAction() == keyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER )
                {
                    if(edittext.getText() != null)
                    {
                    String input = edittext.getText().toString().replaceAll("[\\t\\n\\r]+","");

                    Note note = new Note(input);
                    toDoList.add(0, note);

                    myarrayAdapter.notifyDataSetChanged();
                    edittext.setText("");
                    //edittext.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),
                                input + " is not good, do something else",
                                Toast.LENGTH_SHORT).show();
                    }



            }
                return false;
            }
        }
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public void onConfigurationChanged(Configuration   newconfig)
    {
        super.onConfigurationChanged(newconfig);
        if(newconfig.orientation != Configuration.ORIENTATION_LANDSCAPE)
        {
            Toast.makeText(this, "go home yankey", Toast.LENGTH_SHORT).show();
        }
    }

}
    

