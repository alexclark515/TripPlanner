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
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	// Declare variables for widgets
	/*
	 * private TextView viewNextTrip; private Button planATrip; private Button
	 * tripHistory; private Button viewMap; private Button viewAlerts; private
	 * Button googleSearch; private Button saveLocation;
	 */
	private SQLHelper helper;
	// private PackList packList;
	private SQLiteDatabase db;
	private static String selectedTripName = "";
	// private Calendar cal = Calendar.getInstance();

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

		// Play a fade in animation of the MainActivity Layout.
		LinearLayout layout = (LinearLayout) findViewById(R.id.linearId);
		AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setFillAfter(true);
		animation.setDuration(2500);

		layout.startAnimation(animation);

		aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, trips);
		setListAdapter(aa);

		// create database
		try {
			db = helper.getWritableDatabase();
		} catch (SQLException e) {
			Log.d("SQLite", "Create database failed");
		}
		this.refreshList();
	}

	// Creates the Options Menu
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add(0, PICK1, Menu.NONE, "New Trip");
		MenuItem item2 = menu.add(0, PICK2, Menu.NONE, "View Trip");
		MenuItem item3 = menu.add(0, PICK3, Menu.NONE, "View Map");
		MenuItem item4 = menu.add(0, PICK4, Menu.NONE, "Search Google");
		MenuItem item5 = menu.add(0, PICK5, Menu.NONE, "Delete Trip");

		return true;
	}

	// Handles menu item selection
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		switch (itemId) {

		// Starts NewTrip activity
		case PICK1:
			startActivity(new Intent(this, NewTrip.class));
			return true;

			// Starts ViewTripActivity
		case PICK2:
			if (!(MainActivity.selectedTripName.equals(""))) {
				Intent intent = new Intent(this, ViewTrip.class);
				intent.putExtra("trip_name", selectedTripName);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Select a trip", Toast.LENGTH_SHORT)
						.show();
			}
			return true;

			// Starts Google Maps Activity
		case PICK3:
			startActivity(new Intent(this, GoogleMaps.class));
			return true;

			// Starts Google Search Activity
		case PICK4:
			startActivity(new Intent(this, GoogleSearch.class));
			return true;

			// Deletes trip from database and list
		case PICK5:
			if (!(MainActivity.selectedTripName.equals(""))) {
				Trip t = helper.getTripByName(selectedTripName);
				helper.deleteTrip(t.getID());
				this.refreshList();
				selectedTripName = "";
				this.getListView().clearChoices();
			} else {
				Toast.makeText(this, "Select a trip to delete",
						Toast.LENGTH_SHORT).show();
			}
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

	// Refreshes list on resume
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
