package com.example.intentsample2;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class BActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);
		
		
		Intent launchingIntent = getIntent();
		
		TextView showMessage = (TextView) findViewById(R.id.lblShowMessage);

        Bundle bundl = launchingIntent.getExtras();

        if(bundl != null)
        {
            String message = launchingIntent.getStringExtra("ShowMessage");
            if(!message.isEmpty())
            {
		        showMessage.setText(message);
            }
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.b, menu);
		return true;
	}

}
