package com.aryef.samples.mapssample;

import android.app.Dialog;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.os.Bundle;
import android.app.Activity;

import android.widget.Toast;

import static com.google.android.gms.common.GooglePlayServicesUtil.getErrorDialog;
import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

public class MainActivity extends Activity {
	 
    // Google Map
    private GoogleMap      googleMap;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* int i = isGooglePlayServicesAvailable(getApplicationContext());

        if( i != ConnectionResult.SUCCESS)
        {
            Dialog
                    d = getErrorDialog(i, this, PLAY_SERVICES_RESOLUTION_REQUEST);
            d.show();

        }*/

        setContentView(R.layout.activity_main);
 
        /*try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }*/
 
    }
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
 
}
