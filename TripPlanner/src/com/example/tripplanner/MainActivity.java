package com.example.tripplanner;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	// Declare variables for widgets
	private TextView viewNextTrip;
	private Button planATrip;
	private Button tripHistory;
	private Button viewMap;
	private Button viewAlerts;
	private Button googleSearch;
	private Button saveLocation;
	private SQLHelper helper;
	private PackList packList;
	private SQLiteDatabase db;
	private static String selectedTripName = "";
	private Calendar cal = Calendar.getInstance();

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

		helper = new SQLHelper(this);
		setContentView(R.layout.activity_main);
		aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, trips);
		setListAdapter(aa);

		// create database
		try {
			db = helper.getWritableDatabase();
		} catch (SQLException e) {
			Log.d("SQLite", "Create database failed");
		}

		// Dummy Trips - Delete this code when finished
		helper.deleteAll();
		Trip trip = new Trip("Peru", "Arequipa", "2015-06-01", "2015-07-01");
		Trip trip2 = new Trip("Nepal", "Kathmandu", "2016-10-01", "2016-11-01");
		Trip trip3 = new Trip("Argentina", "Patagonia", "2015-12-01",
				"2016-01-01");
		helper.addTrip(trip);
		helper.addTrip(trip2);
		helper.addTrip(trip3);
		this.packList = new PackList(trip);

		TripListItem item1 = new TripListItem(this.packList, "Shoes");
		TripListItem item2 = new TripListItem(this.packList, "Boots");
		TripListItem item3 = new TripListItem(this.packList, "Rope", true);

		helper.saveList(this.packList);
		// ******************************************************/

		this.refreshList();
	}

	// Creates the Options Menu
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add(0, PICK1, Menu.NONE, "New Trip");
		MenuItem item2 = menu.add(0, PICK2, Menu.NONE, "View Trip");
		MenuItem item3 = menu.add(0, PICK3, Menu.NONE, "View Map");
		MenuItem item4 = menu.add(0, PICK4, Menu.NONE, "Search Google");
		MenuItem item5 = menu.add(0, PICK5, Menu.NONE, "View Alerts");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		switch (itemId) {

		// New Trip
		case PICK1:
			startActivity(new Intent(this, NewTrip.class));
			return true;
			// View Trip
		case PICK2:
			if (!(MainActivity.selectedTripName.equals(""))) {
				Intent intent = new Intent(this, ViewTrip.class);
				intent.putExtra("trip_name", selectedTripName);
				startActivity(intent);
			}
			return true;
			// View Map
		case PICK3:
			startActivity(new Intent(this, GoogleMaps.class));
			Toast.makeText(this, "Long tap to add marker", Toast.LENGTH_LONG)
					.show();
			return true;
			// Search Google
		case PICK4:
			startActivity(new Intent(this, GoogleSearch.class));
			Toast.makeText(
					this,
					"You clicked the " + googleSearch.getText().toString()
							+ " Button.", Toast.LENGTH_LONG).show();
			return true;
			// View Alerts [CURRENTLY JUST ADDING TRIPS]
		case PICK5:
			for (int i = 0; i < 15; i++) {
				trips.add("Test List Items " + i);
			}
			aa.notifyDataSetChanged();
			return true;
		}
		return false;
	}

	/* @Override This method returns the name of the item selected */
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Object object = this.getListAdapter().getItem(position);
		selectedTripName = (String) object;
	}

	protected void onResume() {
		super.onResume();
		this.refreshList();
	}

	// Store all trips in db to array list, get names and adapt to list view
	// widget
	public void refreshList() {
		trips.clear();

		ArrayList<Trip> allTrips = helper.getTrips();
		for (Trip t : allTrips) {
			this.trips.add(t.getName());
		}

		aa.notifyDataSetChanged();
	}
}
