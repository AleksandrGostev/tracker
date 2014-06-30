package com.miscdev.tracker;

import com.miscdev.tracker.R;
import com.miscdev.tracker.services.LocationTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
	static GoogleMap mMap;
	static Context context;
	LocationTracker mGPS;
	private Timer myTimer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//context = getApplicationContext();
		setContentView(R.layout.activity_main);
		mMap  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		mMap.getUiSettings().setZoomControlsEnabled(false);
		mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		/*map.setOnMapClickListener(this);
		map.setOnMapLongClickListener(this);
		map.setOnMarkerClickListener(this);
		map.setOnInfoWindowClickListener(this);*/
		
		
		mGPS = new LocationTracker(this, mMap);

        if(mGPS.canGetLocation){
            double mLat=mGPS.getLatitude();
            double mLong=mGPS.getLongitude();
            LatLng position = new LatLng(mLat, mLong);
            // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(position)      // Sets the center of the map to Mountain View
                    .zoom(17)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            //Set marker on this position
            mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title("You!")).showInfoWindow();

        }else{

        }

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 10000);
		
	}
	
	private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }
    
    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.
            Location loc = mGPS.getLocation();
            mGPS.updateLocation(loc);
            Log.v("Location_SELF", "Location: " + loc);
            //Do something to the UI thread here

        }
    };

}
