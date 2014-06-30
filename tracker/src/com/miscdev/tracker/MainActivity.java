package com.miscdev.tracker;

import com.miscdev.tracker.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class MainActivity extends Activity {
	static GoogleMap map;
	static Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//context = getApplicationContext();
		setContentView(R.layout.activity_main);
		map  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		map.getUiSettings().setZoomControlsEnabled(false);
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		/*map.setOnMapClickListener(this);
		map.setOnMapLongClickListener(this);
		map.setOnMarkerClickListener(this);
		map.setOnInfoWindowClickListener(this);*/
		
	}

}
