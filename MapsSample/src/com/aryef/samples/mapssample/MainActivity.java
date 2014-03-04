package com.aryef.samples.mapssample;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import android.support.v4.app.FragmentActivity;

import java.io.IOException;

import static com.google.android.gms.common.GooglePlayServicesUtil.getErrorDialog;
import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

public class MainActivity extends FragmentActivity {

    // Google Map
    private GoogleMap googleMap;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	String SENDER_ID = "538459826415";
	static final String TAG = "GCMDemo";
	GoogleCloudMessaging gcm;
	TextView mDisplay;
	String regid;
	Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int i = isGooglePlayServicesAvailable(getApplicationContext());

        if (i != ConnectionResult.SUCCESS) {
            Dialog
                    d = getErrorDialog(i, this, PLAY_SERVICES_RESOLUTION_REQUEST);
            d.show();

        }
		//gcm = GoogleCloudMessaging.getInstance(this);
		//new RegisterBackground().execute();

		setContentView(R.layout.activity_main);

        try {
            // Loading map
            //   initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(31.4388, 35.02816)).zoom(12).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  initilizeMap();
    }

	class RegisterBackground extends AsyncTask<String,String,String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String msg = "";
			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(context);
				}
				regid = gcm.register(SENDER_ID);
				msg = "Dvice registered, registration ID=" + regid;
				Log.d("111", msg);
				sendRegistrationIdToBackend(regid);

			} catch (IOException ex) {
				msg = "Error :" + ex.getMessage();
			}
			return msg;
		}
		private void sendRegistrationIdToBackend(String regid) {
			// this code will send registration id of a device to our own server.
		}
}
}
