package com.example.intentsample2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		
		Button btnExplicit =(Button) findViewById(R.id.btnStartExplicit);
		Button btnImplicit =(Button) findViewById(R.id.startImplicit);
		
		btnExplicit.setOnClickListener(myExplicitlistener);
		btnImplicit.setOnClickListener(myImplicitlistener);
		
		
		
		return true;
	}
	
	private OnClickListener myExplicitlistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			EditText editText = (EditText) findViewById(R.id.txtMessage);
			
			Intent intentExplicit = new Intent(getApplicationContext(), BActivity.class) ;
			
			intentExplicit.putExtra("ShowMessage", editText.getText().toString());
			startActivity(intentExplicit);
			
		}
	};
	
private OnClickListener myImplicitlistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			EditText editText = (EditText) findViewById(R.id.txtMessage);
			
			Intent intentExplicit = new Intent("com.example.ActivityB") ;
           // IntentResolver

			intentExplicit.putExtra("ShowMessage", editText.getText().toString());
			startActivity(intentExplicit);
			
		}
	};
	
}
