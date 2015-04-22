package com.example.tripplanner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
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
	private Calendar cal = Calendar.getInstance();
	private int dateType;
	private String sqlStartDate;
	private String sqlEndDate;
	private Intent packList;

	// This format should be used for showing in the edit text
	private SimpleDateFormat date_fmt = new SimpleDateFormat("EEE, MMM d, yyyy");

	// This format should be used for writing to the database
	private SimpleDateFormat date_fmt2 = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_trip);
		packList = new Intent(this, PackingList.class);

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

		// Date picker dialog is the pop up date picker
		final DatePickerDialog datePicker = new DatePickerDialog(this, 0,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {

						// The Gregorian calendar is a good way to store dates
						// in Java
						Calendar cal = new GregorianCalendar(year, monthOfYear,
								dayOfMonth, 0, 0, 0);

						// If the dateType is 0 then it writes the text to the
						// start date
						// If it's 1 it writes to the end date
						if (dateType == 0) {
							startDate.setText(date_fmt.format(cal.getTime()));
							sqlStartDate = (date_fmt2.format(cal.getTime()));
						} else {
							endDate.setText(date_fmt.format(cal.getTime()));
							sqlEndDate = (date_fmt2.format(cal.getTime()));
						}

					}
				}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));

		// On click listener for the edit text
		startDate.setOnClickListener(new OnClickListener() {

			// This method is called when the edit text is clicked
			@Override
			public void onClick(View v) {
				dateType = 0;
				datePicker.show();
			}
		});

		endDate.setOnClickListener(new OnClickListener() {

			// This method is called when the edit text is clicked
			@Override
			public void onClick(View v) {
				dateType = 1;
				datePicker.show();
			}
		});

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
			this.saveTrip();
			break;

		// If Packing List Button is clicked.
		case R.id.btnPackingListNT:

			if (newTrip != null) {
				// Pass Trip name to NewTrip Class if there is a new trip
				packList.putExtra("Trip", newTrip.getName());
				startActivity(new Intent(this, PackingList.class));
			} else {
				Toast.makeText(NewTrip.this, "No Trip Selected",
						Toast.LENGTH_SHORT).show();
			}
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
		String msg = "All Fields Required";
		String msg2 = "Please choose a unique trip name";
		String name = tripName.getText().toString();
		String dest = destination.getText().toString();
		String start = startDate.getText().toString();
		String end = endDate.getText().toString();

		if (!(name.equals("") || dest.equals("") || start.equals("") || end
				.equals(""))) {

			newTrip = new Trip(name, dest, sqlStartDate, sqlEndDate);

			if (helper.isUnique(newTrip)) {
				helper.addTrip(newTrip);
			} else {
				Toast.makeText(NewTrip.this, msg2, Toast.LENGTH_LONG).show();
			}

		} else {
			Toast.makeText(NewTrip.this, msg, Toast.LENGTH_LONG).show();
		}
	}

	/***************
	 * Example code for creating trips and lists, and list
	 * items****************************************** Trip trip = new
	 * Trip("Peru", "Arequipa", "2015-06-01", "2015-07-01"); Trip trip2 = new
	 * Trip("Nepal", "Kathmandu", "2016-10-01", "2016-11-01");
	 * 
	 * //Save Trip to DB helper.addTrip(trip); helper.addTrip(trip2);
	 * 
	 * TripList todo = new ToDoList(trip);
	 * 
	 * TripListItem item1 = new TripListItem(todo, "Climb a mountain");
	 * TripListItem item2 = new TripListItem(todo, "Rock Climb"); TripListItem
	 * item3 = new TripListItem(todo, "Ice Climb");
	 * 
	 * todo.add(item1); todo.add(item2); todo.add(item3);
	 * 
	 * helper.saveList(todo);
	 * 
	 * todo.remove(1); helper.saveList(todo); Trip trip =
	 * helper.getTripByID(10);
	 * 
	 * Trip trip = helper.getTripByID(10); helper.loadToDoList(trip); ToDoList
	 * todo = trip.getToDoList(); for (TripListItem i : todo){
	 * Toast.makeText(this,i.toString(), Toast.LENGTH_LONG).show();}
	 *******************************************************************/
}
