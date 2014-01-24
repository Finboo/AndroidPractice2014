package com.aryef.samples.notessavedinfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;

import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aryef.samples.notesevenmoreextended.R;

public class MainActivity extends Activity {

    private ArrayList<Notes> mItems;
    private DataControllerAdapter adapter;
    private EditText edittext;
    private ListView listview;
    private SharedPreferences sharedPreferences;
    private long activated = 0;
    private final static String FILE_NAME = "notes_db22";
    private AlertDialog.Builder dialogbuilder;

    //
    //get db from file
    //
    private ArrayList<Notes> GetDataFromFile() {
        ArrayList<Notes> ret = new ArrayList<Notes>();
        FileInputStream fis = null;
        DataInputStream dis = null;

        try {

             fis = new FileInputStream(getFilesDir() + "/" + FILE_NAME);
             dis = new DataInputStream(fis);

            //get number of records in the database
            int size = dis.readInt();

            for (int i = 0; i < size; i++) {
                //get length of the current record;
                int strLength = dis.readInt();
                //get record
                if(strLength>0)
                {
                    String input = "";
                    long timestamp = 0;


                    byte[] bytes = new byte[strLength];
                    try
                    {
                         dis.readFully(bytes);
                         input = new String(bytes);

                        //get timestamp
                         timestamp = dis.readLong();

                    }
                    catch (IOException
                            rx)
                    {
                        Log.e("aryetag3", rx.getMessage());
                    }
                //convert record bytes to string

                //ad retreived record to in-memory db object
                    if(input!= "")
                {
                    ret.add(new Notes(input, timestamp));
                }

                }


            }

            if(dis != null)
                dis.close();
            if (fis != null)
                fis.close();


        } catch (Exception e) {
            Log.e("aryetag2", e.getMessage());
        }


        return ret;
    }

    //save db to file
    @SuppressWarnings("unused")
    private void SaveDataToFile(final ArrayList<Notes> items) {
        try {
            FileOutputStream fos = new FileOutputStream(getFilesDir() + "/" + FILE_NAME);
            DataOutputStream dos = new DataOutputStream(fos);

            // get db size
            int size = items.size();
            //write number of records to be stored
            dos.writeInt(size);

            for (int i = 0; i < size; i++) {
                //get record's size
                int itemlength = (items.get(i)).mLabel.getBytes().length;

                //get item's data
                String item = items.get(i).mLabel;

                //write item's length to file
                dos.writeInt(itemlength);
                //write item's data bytes to file
                dos.write(item.getBytes());
                //write time stamp
                dos.writeLong(items.get(i).mTimeStamp);

            }

        } catch (Exception e) {
            Log.e("aryetag1", e.getMessage());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SaveDataToFile(mItems);


    }

    @Override
    protected void
    onResume() {
        super.onResume();

        edittext = (EditText) findViewById(R.id.myeditText);
        listview = (ListView) findViewById(R.id.mylistView);

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
        mItems = GetDataFromFile();
        if (mItems == null) {
            mItems = new ArrayList<Notes>();
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
        edittext.setOnKeyListener(new OnKeyListener() {

            @Override
            @SuppressWarnings("NullPointerException")
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (edittext != null && !edittext.getText().toString().equals("")) {

                        String note = edittext.getText().toString().replaceAll("[\\t\\n\\r]+", "");

                        mItems.add(0, new Notes(note));
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
                                    ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.main, menu);
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


        Notes message = mItems.get(position);

        switch (id) {
            case R.id.remove_item:
                mItems.remove(position);

                break;
            case R.id.share_item:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "arye.friedman@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, message.mLabel);
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


                    mItems.add(0, new Notes(note));

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

            if (view != null && view.findViewById(R.id.mynote) != null && mItems != null
                    && mItems.get(position) != null) {
                ((TextView) view.findViewById(R.id.mynote)).setText(mItems.get(position).mLabel);
                ((TextView) view.findViewById(R.id.mytimestamp)).setText(new Date(mItems.get(position).mTimeStamp).toString());
            }

            return view;
        }

    }

}
