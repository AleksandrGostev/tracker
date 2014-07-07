package com.miscdev.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miscdev.DTO.User;
import com.miscdev.tracker.services.LocationTracker;
import com.miscdev.tracker.services.UserHttpRequest;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MapActivity extends Activity {
	
	static GoogleMap mMap;
	static Context context;
	LocationTracker mGPS;
	private Timer myTimer;
	private String URL = "http://misc-dev.com/api/users";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//context = getApplicationContext();
		setContentView(R.layout.map);
		
		new UserHttpRequest().execute(URL);
		
		
		mMap  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		mMap.getUiSettings().setZoomControlsEnabled(false);
		mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		/*mMap.setOnMapClickListener(this);
		mMap.setOnMapLongClickListener(this);
		mMap.setOnMarkerClickListener(this);
		mMap.setOnInfoWindowClickListener(this);*/
		
		
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
        this.runOnUiThread(Timer_Tick);
    }
    
    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.
            Location loc = mGPS.getLocation();
            mGPS.updateLocation(loc);
            Log.v("Location_SELF", "Location: " + loc);
            new UserHttpRequest().execute(URL);
        }
    };
    
    public static void managePins(String json) {
    	Gson gson = new Gson();
    	List<User> users = new ArrayList<User>(); 
        users = gson.fromJson(json, new TypeToken<List<User>>(){}.getType());
        int randomDouble = 0; 
        Random randomGenerator = new Random();
        for (int idx = 1; idx <= 3; ++idx){
          randomDouble = randomGenerator.nextInt(100);
        }
        
        if(users != null && !users.isEmpty()) {
	    	for(User u : users) {
	    		if(u.Latitude > 0 && u.Longitude > 0 && u.Username.compareTo(MainActivity.currentUsername) != 0) {
	    			mMap.addMarker(new MarkerOptions()
	                .position(new LatLng(u.Latitude+randomDouble, u.Longitude+randomDouble))
	                .title("Test!")).showInfoWindow();
	    		}
	    	}
        }
    }
}
