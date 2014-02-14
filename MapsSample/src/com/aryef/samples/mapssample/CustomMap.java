package com.aryef.samples.mapssample;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by arye on 13/02/14.
 */
public class CustomMap extends SupportMapFragment {

    private static final int ZOOMIN_ANIMATION_DURATION = 300;

    public static CustomMap newInstance() {
        Bundle bundle = new Bundle();
        CustomMap customMap = new CustomMap();
        customMap.setArguments(bundle);
        return customMap;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        GoogleMap googleMap = getMap();
        if (googleMap == null) {
        } else {
            LatLng mLatLng = new LatLng(70.00,
                    33.00);

            googleMap.addMarker(new MarkerOptions()
                    .title("Title")
                    .snippet("Address")
                    .position(mLatLng)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_launcher)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomBy(10),
                    ZOOMIN_ANIMATION_DURATION, null);
        }
    }
}
