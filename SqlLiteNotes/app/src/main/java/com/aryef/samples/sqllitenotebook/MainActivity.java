package com.aryef.samples.sqllitenotebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {

    private ArrayList<Note> mItems;
    private DataControllerAdapter adapter;
    private EditText edittext;
    private ListView listview;
    private SharedPreferences sharedPreferences;
    private long activated = 0;

    private AlertDialog.Builder dialogbuilder;

    @Override
    protected void onPause() {
        super.onPause();

       // SaveDataToFile(mItems);
        NotesDatabase.GetInstance().SaveAllNotes(mItems);

    }

    @Override
    protected void
    onResume() {
        super.onResume();

        edittext = (EditText) findViewById(R.id.editText);
        listview = (ListView) findViewById(R.id.listView);

        registerForContextMenu(listview);

        CheckLicense();
        InitiateData();
        BindUI_ToDataAdapter();
        SetKeyEventOnInputEditBox();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Cookie", Context.MODE_PRIVATE);

        CreateActivatioDialog();
        //register view for context menu;


    }

    private void CheckLicense() {

        activated = sharedPreferences.getLong("activated", 0);

        if (activated == 0) {
            AlertDialog alert = dialogbuilder.create();
            alert.show();

        }

    }

    private void ShowToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void BindUI_ToDataAdapter() {
        adapter = new DataControllerAdapter();
        listview.setAdapter(adapter);
    }

    private void InitiateData() {

        mItems = NotesDatabase.GetInstance(getApplicationContext()).getNotes();

        if (mItems == null) {
            mItems = new ArrayList<Note>();
        }
    }

    private void CreateActivatioDialog() {
        dialogbuilder = new AlertDialog.Builder(this);
        dialogbuilder.setMessage("Please sign on agreement you bastard!")
                .setCancelable(false)
                .setPositiveButton("Yes, I agree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SaveActivation(System.currentTimeMillis());
                        ShowToast("yoffi");

                    }
                }).setNegativeButton("No, Never", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SaveActivation(0);
                ShowToast("go home");
                MainActivity.this.finish();
            }
        });

    }

    private void SaveActivation(long activatedMillis) {

        sharedPreferences.edit().putLong("activated", activatedMillis).commit();

        activated = activatedMillis;

    }

    private void SetKeyEventOnInputEditBox() {
        edittext.setOnKeyListener(new View.OnKeyListener() {

            @Override
            @SuppressWarnings("NullPointerException")
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (edittext != null && !edittext.getText().toString().equals("")) {

                        String note = edittext.getText().toString().replaceAll("[\\t\\n\\r]+", "");

                        mItems.add(0, new Note(note));
                        adapter.notifyDataSetChanged();
                        edittext.setText("");
                    }

                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.listview_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //context chooser menu handler
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        int position = 0;
        //patent !!!
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//also works
        //position = listview.getSelectedItemPosition();
        if (aInfo != null) {
            position = aInfo.position;
        }


        Note message = mItems.get(position);

        switch (id) {
            case R.id.remove_item:
                mItems.remove(position);

                break;
            case R.id.share_item:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "arye.friedman@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, message.getNote());
                startActivity(Intent.createChooser(emailIntent, "Send email..."));


                break;


            default:
                break;
        }

        adapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

    //Main activity menu chooser handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.clear_all:
                mItems.clear();

                break;
            case R.id.add_new_item:

                if (edittext != null && !edittext.getText().toString().equals("")) {
                    String note = edittext.getText().toString().replaceAll("[\\t\\n\\r]+", "");


                    mItems.add(0, new Note(note));

                    edittext.setText("");
                    edittext.setVisibility(View.GONE);
                }
                break;
            case R.id.enable_editing:

                edittext.setVisibility(View.VISIBLE);
            default:
                break;

            case R.id.check_license:
                SaveActivation(0);
                CheckLicense();
                break;
        }

        adapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    //Controller innner class implementation
    class DataControllerAdapter extends BaseAdapter {
        LayoutInflater inflater;

        DataControllerAdapter() {
            Context context = getApplicationContext();
            if (context != null)
                inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {

            return mItems.size();
        }

        @Override
        public Object getItem(int position) {

            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                view = inflater.inflate(R.layout.note_layout, parent, false);
            } else {
                view = convertView;
            }

            if (view != null && view.findViewById(R.id.note_text) != null && mItems != null
                    && mItems.get(position) != null)
            {

                //((TextView) view.findViewById(R.id.note_text)).setText(mItems.get(position).getNote().toString());

                ((TextView) view.findViewById(R.id.note_text)).setText(mItems.get(position).getNote().toString());


                ((TextView) view.findViewById(R.id.notedate)).setText(new Date(mItems.get(position).getTime()).toString());

            }

            return view;
        }

    }

}
