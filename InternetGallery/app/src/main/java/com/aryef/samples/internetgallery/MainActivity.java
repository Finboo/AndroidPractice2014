package com.aryef.samples.internetgallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends Activity {

    ArrayList<Drawable> myimages = new ArrayList<Drawable>();
    ImageAdapter myimageAdapter;

    Gallery mGalllery;

    Handler mHandler = new Handler(){


        @Override
    public void handleMessage(Message msg)
        {
            myimageAdapter.notifyDataSetChanged();
            super.handleMessage(msg);


        }

    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        mGalllery = (Gallery) findViewById(R.id.gallery);
        ImageView imageview = (ImageView) findViewById(R.id.imageview);
myimageAdapter = new ImageAdapter(getApplicationContext());
        mGalllery.setAdapter(myimageAdapter);

      AdapterView.OnItemSelectedListener listener = new Listener();
        mGalllery.setOnItemSelectedListener(listener);

        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread ()
                {
                    public void run()
                    {
                        GetBitmapFromUrl("http://s.codeproject.com/App_Themes/CodeProject/Img/logo250x135.gif");
                    }

                }.start();

            }
        });



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    private  void GetBitmapFromUrl(String urlstring)
    {
        try
        {
            URL url = null;
            try {
                url = new URL(urlstring);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.connect();
        InputStream input = conn.getInputStream();

        Bitmap bitmap = BitmapFactory.decodeStream(input);
        Drawable d = new BitmapDrawable(getResources(), bitmap);

        myimages.add(d);
        mHandler.sendEmptyMessage(1);
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


public  class ImageAdapter extends BaseAdapter
{
private Context mContext;

    public ImageAdapter(Context context)
    {
        mContext = context;
    }

    @Override
    public int getCount() {
        return myimages.size();
    }

    @Override
    public Object getItem(int i) {
        return myimages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView imageView = new ImageView(mContext);
        Drawable d = myimages.get(i);

        imageView.setBackground(d);
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        return imageView;
    }
}

    class Listener implements AdapterView.OnItemSelectedListener
    {





        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            SetMainImage(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    public void SetMainImage(int position)
    {
        ( (ImageView) findViewById(R.id.mainimage)).setImageDrawable(myimages.get(mGalllery.getSelectedItemPosition()));
    }
}
