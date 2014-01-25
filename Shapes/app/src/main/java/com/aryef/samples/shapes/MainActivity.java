package com.aryef.samples.shapes;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
int RED = 0xffff0000;
    int BLUE = 0xff0000ff;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button1);

        ListView listView = (ListView) findViewById(R.id.list1);


       // View container = findViewById(R.id.mainview1);
        View container = findViewById(R.id.button1);

        ValueAnimator alphaAnim = ObjectAnimator.ofFloat(container, "alpha", 0.0f, 0.9f);
        alphaAnim.setDuration(5000);
        alphaAnim.setEvaluator(new FloatEvaluator());
        alphaAnim.setRepeatCount(ValueAnimator.INFINITE);
        alphaAnim.setRepeatMode(ValueAnimator.REVERSE);
        //alphaAnim.start();

        ValueAnimator colorAnim = ObjectAnimator.ofInt(container, "backgroundColor", RED, BLUE);
        colorAnim.setDuration(15000);

        colorAnim.setEvaluator(new ArgbEvaluator());


        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);



		return true;
	}

}
