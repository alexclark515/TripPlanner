package com.example.tripplanner;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	//Declare variables for widgets
	private TextView viewNextTrip;
	private Button planATrip;
	private Button tripHistory;
	private Button viewMap;
	private Button viewAlerts;
	private Button googleSearch;
	private Button saveLocation;
	
	final int PICK1 = Menu.FIRST + 1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Link Java variables with xml widgets
		//Set OnClickListener on Buttons
		viewNextTrip = (TextView) findViewById(R.id.viewNextTrip);
		planATrip = (Button) findViewById(R.id.btnPlanATrip);
		planATrip.setOnClickListener(this);
		tripHistory = (Button) findViewById(R.id.btnTripHistory);
		tripHistory.setOnClickListener(this);
		viewMap = (Button) findViewById(R.id.btnViewMap);
		viewMap.setOnClickListener(this);
		viewAlerts = (Button) findViewById(R.id.btnViewAlerts);
		viewAlerts.setOnClickListener(this);
		googleSearch = (Button) findViewById(R.id.btnGoogleSearch);
		googleSearch.setOnClickListener(this);
		saveLocation = (Button) findViewById(R.id.btnSaveLocation);
		saveLocation.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
	    //Currently test code to make sure buttons are working.   
		switch(v.getId()){
		
		//If Plan a Trip Button is clicked. 
		case R.id.btnPlanATrip:
			startActivity(new Intent (this,NewTrip.class));
		break;
		
		//If Trip History Button is clicked. 
		case R.id.btnTripHistory:
			Toast.makeText(this,"You clicked the " + 
					tripHistory.getText().toString()+ " Button.",Toast.LENGTH_LONG)
			.show();
		break;
		
		//If View Map Button is clicked. 
		case R.id.btnViewMap:
			Toast.makeText(this,"You clicked the " + 
					viewMap.getText().toString()+ " Button.",Toast.LENGTH_LONG)
			.show();
		break;
		
		//If View Alerts is clicked. 
		case R.id.btnViewAlerts:
			Toast.makeText(this,"You clicked the " + 
					viewAlerts.getText().toString()+ " Button.",Toast.LENGTH_LONG)
			.show();
		break;
		
		//If Google Search Button is clicked. 
		case R.id.btnGoogleSearch:
		Toast.makeText(this,"You clicked the " + 
				googleSearch.getText().toString()+ " Button.",Toast.LENGTH_LONG)
		.show();
		break;
		
		//If Save Location Button is clicked. 
		case R.id.btnSaveLocation:
			Toast.makeText(this,"You clicked the " + 
					saveLocation.getText().toString()+ " Button.",Toast.LENGTH_LONG)
			.show();
		break;	      
	    }
		
		
	}
	
	//Creates the Options Menu
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add(0,PICK1,Menu.NONE,"Plan Trip");
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int itemId = item.getItemId();
		
		switch (itemId){
		
		case PICK1:
			startActivity(new Intent (this,NewTrip.class));
		
			return true;
		}
		return false;
	}
}
