package com.example.tripplanner;


import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	//Declare variables for widgets
	private TextView viewNextTrip;
	private Button planATrip;
	private Button tripHistory;
	private Button viewMap;
	private Button viewAlerts;
	private Button googleSearch;
	private Button saveLocation;
	
	final int PICK1 = Menu.FIRST + 1;
	final int PICK2 = Menu.FIRST + 2;
	final int PICK3 = Menu.FIRST + 3;
	final int PICK4 = Menu.FIRST + 4;
	final int PICK5 = Menu.FIRST + 5;
	
	ArrayList<String> trips = new ArrayList<String>();
	ArrayAdapter<String> aa;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		aa = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_list_item_single_choice,
				trips);
		
		setListAdapter(aa);		

	}
	
	//Creates the Options Menu
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add(0,PICK1,Menu.NONE,"New Trip");
		MenuItem item2 = menu.add(0,PICK2,Menu.NONE, "View Trip");
		MenuItem item3 = menu.add(0,PICK3,Menu.NONE, "View Map");
		MenuItem item4 = menu.add(0,PICK4,Menu.NONE, "Search Google");
		MenuItem item5 = menu.add(0,PICK5,Menu.NONE, "View Alerts");	
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int itemId = item.getItemId();
		
		switch (itemId){
		
		//New Trip
		case PICK1:
			startActivity(new Intent (this,NewTrip.class));		
			return true;
		//View Trip
		case PICK2:	
			return true;
		//View Map	
		case PICK3:
			return true;
		//Search Google
		case PICK4:
			return true;
		//View Alerts [CURRENTLY JUST ADDING TRIPS]	
		case PICK5:
			for(int i = 0; i <15; i++){
				trips.add("Test List Items " + i);
			}		
			aa.notifyDataSetChanged();
			return true;
		}
		return false;
	}
}
