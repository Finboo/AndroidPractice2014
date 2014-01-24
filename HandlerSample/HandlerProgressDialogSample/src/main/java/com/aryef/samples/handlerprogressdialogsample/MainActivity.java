package com.aryef.samples.handlerprogressdialogsample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    ProgressDialog mdialog;

    Thread thread_ProgressSender;
    ProgressSender progress_sender;
    public static ProgressReceiver shandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
shandler = new ProgressReceiver(this, getResources().getString(R.string.progress_message_key));

        final Button btnStartService = ((Button) findViewById(R.id.buttonStartProgress));

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdialog = new ProgressDialog(MainActivity.this);
                mdialog.setTitle("Downloading...");
                mdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mdialog.show();

                StartProgressDialog();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    void  StartProgressDialog(){

        progress_sender = new ProgressSender(getResources().getString(R.string.progress_message_key));
        thread_ProgressSender = new Thread(progress_sender);
        thread_ProgressSender.start();
    }
}
