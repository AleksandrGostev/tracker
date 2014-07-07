package com.miscdev.tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public static String currentUsername;
	Button loginBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loginBtn = (Button) findViewById(R.id.lgnBtn);
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText et = (EditText)findViewById(R.id.currentUserName);
				currentUsername =  et.getText().toString();
				Log.v("JSON_RET", currentUsername);
				
				Intent goToNextActivity = new Intent(getApplicationContext(), MapActivity.class);
				startActivity(goToNextActivity);
			}
			
		});
		
	}

}
