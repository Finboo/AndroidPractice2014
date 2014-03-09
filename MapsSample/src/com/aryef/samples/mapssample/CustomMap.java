package com.aryef.samples.mapssample;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

/**
 * Created by arye on 13/02/14.
 */
public class CustomMap extends SupportMapFragment {

    private static final int ZOOMIN_ANIMATION_DURATION = 300;


// --Commented out by Inspection START (13:48 07/03/14):
//    public static CustomMap newInstance() {
//        Bundle bundle = new Bundle();
//        CustomMap customMap = new CustomMap();
//        customMap.setArguments(bundle);
//        return customMap;
//    }
// --Commented out by Inspection STOP (13:48 07/03/14)

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
		InitializeMap();
    }

    @Override
    public void onResume() {
        super.onResume();
		InitializeMap();
    }

	private void InitializeMap() {
		GoogleMap googleMap = getMap();
		if (googleMap == null)
		{
//			Toast.makeText(getApplicationContext(),
//					"Sorry! unable to create maps", Toast.LENGTH_SHORT)
//					.show();
		}
		else
		{
			LatLng mLatLng = new LatLng(31.44130,
					35.02916);

			googleMap.addMarker(new MarkerOptions()
					.title("Title")
					.snippet("Address")
					.position(mLatLng)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.ic_launcher)));

			googleMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			Sleep(1000);

			googleMap.animateCamera(CameraUpdateFactory.zoomBy(10),
					ZOOMIN_ANIMATION_DURATION, null);
			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			Sleep(1000);

			googleMap.getUiSettings().setCompassEnabled(true);
			googleMap.getUiSettings().setZoomControlsEnabled(true);

			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			googleMap.getUiSettings().setZoomControlsEnabled(true);
			googleMap.getUiSettings().setZoomGesturesEnabled(true);

			googleMap.setTrafficEnabled(true);

			googleMap.animateCamera(CameraUpdateFactory.zoomIn());
			googleMap.animateCamera(CameraUpdateFactory.zoomOut());

			Location myLocation = googleMap.getMyLocation();

			googleMap.addPolyline(new PolylineOptions()
					.add(new LatLng(31.4388, 35.02816),
							new LatLng(myLocation.getLatitude(), myLocation.getLongitude())).
							width(5).color(Color.RED));

			float cameraZoom = 10;
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, cameraZoom));

			googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			Sleep(1000);

			CameraPosition cameraPosition = new CameraPosition.Builder().target(
					new LatLng(31.4388, 35.02816)).zoom(15).build();

			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

			((MainActivity) getActivity()).onResume();
		}
	}

	private static void Sleep(long sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
