package com.aryef.samples.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
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
    Button btnAdd;
    ListView listView;
    final ArrayList<Note> toDoList = new ArrayList<Note>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittext = (EditText) findViewById(R.id.myeditText);
        btnAdd = (Button) findViewById(R.id.mybutton);
        listView =(ListView) findViewById(R.id.mylistView);

        //Controller
        final ArrayAdapter<Note> myarrayAdapter =
                new ArrayAdapter<Note>(this, android.R.layout.simple_expandable_list_item_1, toDoList);

        //bind list view to adapter:

        listView.setAdapter(myarrayAdapter);

        //bind button to event

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = edittext.getText().toString();
                Note note = new Note(input);
                toDoList.add(0, note);
                myarrayAdapter.notifyDataSetChanged();
                edittext.setText("");

                Toast.makeText(getApplicationContext(),
                        input + " is not good, do something else",
                        Toast.LENGTH_SHORT).show();
            }
        });
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
    

