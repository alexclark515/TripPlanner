package com.example.tripplanner;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewTrip extends Activity implements OnClickListener {

	// Declare variables for widgets
	private EditText tripName;
	private EditText destination;
	private EditText startDate;
	private EditText endDate;
	private Button btnCancel;
	private Button btnSave;
	private Button btnPackingList;
	private Button btnToDoList;
	private SQLHelper helper;
	private SQLiteDatabase db;
	private Trip newTrip;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_trip);

		// Link Java variables with xml widgets
		// Set OnClickListener on Buttons
		tripName = (EditText) findViewById(R.id.inputName);
		destination = (EditText) findViewById(R.id.inputDestination);
		startDate = (EditText) findViewById(R.id.startDate);
		endDate = (EditText) findViewById(R.id.endDate);
		btnCancel = (Button) findViewById(R.id.btnCancelNT);
		btnCancel.setOnClickListener(this);
		btnSave = (Button) findViewById(R.id.btnSaveNT);
		btnSave.setOnClickListener(this);
		btnPackingList = (Button) findViewById(R.id.btnPackingListNT);
		btnPackingList.setOnClickListener(this);
		btnToDoList = (Button) findViewById(R.id.btnToDoListNT);
		btnToDoList.setOnClickListener(this);
		helper = new SQLHelper(this);

		// create database
		try {
			db = helper.getWritableDatabase();
		} catch (SQLException e) {
			Log.d("SQLite", "Create database failed");
		}
	}

	public void onClick(View v) {
		// Currently test code to make sure buttons are working.
		switch (v.getId()) {

		// If Cancel Button is clicked.
		case R.id.btnCancelNT:
			Toast.makeText(this, helper.CREATE_TBL_TODO, Toast.LENGTH_LONG)
					.show();
			Toast.makeText(
					this,
					"You clicked the " + btnCancel.getText().toString()
							+ " Button.", Toast.LENGTH_SHORT).show();
			break;

		// If Save Button is clicked.
		case R.id.btnSaveNT:	
			
			//Get the trip information from the EditTexts
			String name = tripName.getText().toString();
			String dest = destination.getText().toString();
			String sDate = startDate.getText().toString();
			String eDate = endDate.getText().toString();
			
			//Create a trip object
			Trip newTrip = new Trip(name,dest,sDate,eDate);
			
			//Clear the EditTexts
			tripName.setText("");
			destination.setText("");
			startDate.setText("");
			endDate.setText("");
			
			//Save the newly created trip to the SQLLite Database. 
			helper.addTrip(newTrip);		
			break;

		// If Packing List Button is clicked.
		case R.id.btnPackingListNT:
			startActivity(new Intent(this, PackingList.class));
			break;

		// If To Do List Button is clicked.
		case R.id.btnToDoListNT:
			Toast.makeText(
					this,
					"You clicked the " + btnToDoList.getText().toString()
							+ " Button.", Toast.LENGTH_SHORT).show();
			break;
		}

	}

	// Method to save data to SQLite database
	public void saveTrip() {
		/**
		 * INSERT CODE HERE: Use the edit texts to convert the users'
		 * information into a Trip object (newTrip declared above). Use the
		 * helper.isUnique(newTrip)) method to make sure the trip name is
		 * unique and make sure that information for the rest of the trip is
		 * filled out (e.g. name, destination start and end dates are filled
		 * in) Create Toasts to let the user know what needs to be done in
		 * order to create the trip.
		 */
	}
	
	/***************
	 * Example code for creating trips and lists, and list
	 * items****************************************** Trip trip = new
	 * Trip("Peru", "Arequipa", "2015-06-01", "2015-07-01"); Trip trip2
	 * = new Trip("Nepal", "Kathmandu", "2016-10-01", "2016-11-01");
	 * 
	 * //Save Trip to DB helper.addTrip(trip); helper.addTrip(trip2);
	 * 
	 * TripList todo = new ToDoList(trip);
	 * 
	 * TripListItem item1 = new TripListItem(todo, "Climb a mountain");
	 * TripListItem item2 = new TripListItem(todo, "Rock Climb");
	 * TripListItem item3 = new TripListItem(todo, "Ice Climb");
	 * 
	 * todo.add(item1); todo.add(item2); todo.add(item3);
	 * 
	 * helper.saveList(todo);
	 * 
	 * todo.remove(1); helper.saveList(todo); Trip trip =
	 * helper.getTripByID(10);
	 * 
	 * Trip trip = helper.getTripByID(10); helper.loadToDoList(trip);
	 * ToDoList todo = trip.getToDoList(); for (TripListItem i : todo){
	 * Toast.makeText(this,i.toString(), Toast.LENGTH_LONG).show();}
	 *******************************************************************/
}
