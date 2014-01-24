package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.helloworld.R;

public class MainActivity extends Activity {

    private  TextView tv;
    private  Button num1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView) findViewById(R.id.numberInput);
		String text = "Arye hey!";

        num1 =(Button) findViewById(R.id.num1Input);
        num1.setOnClickListener(mylistener);
		tv.setText(text);
	}

    private View.OnClickListener mylistener = new View.OnClickListener() {
        public void onClick(View v) {
            tv.setText(num1.getText());
        }
    };
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		return true;
	}

}
