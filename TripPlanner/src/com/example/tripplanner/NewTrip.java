package com.example.tripplanner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class NewTrip extends Activity implements OnClickListener {

	// Declare variables for widgets
	protected EditText tripName;
	protected EditText destination;
	protected EditText startDate;
	protected EditText endDate;
	private Button btnCancel;
	private Button btnSave;
	private Button btnPackingList;
	private Button btnToDoList;
	protected SQLHelper helper;
	private SQLiteDatabase db;
	protected Trip activeTrip;
	private Calendar cal = Calendar.getInstance();
	private int dateType;
	protected String sqlStartDate;
	protected String sqlEndDate;
	private Intent packList;
	private PendingIntent pendingIntent;
	public static final int NOTIFICATION_ID = 1;

	// This format should be used for showing in the edit text
	protected SimpleDateFormat date_fmt = new SimpleDateFormat(
			"EEE, MMM d, yyyy");

	// This format should be used for writing to the database
	protected SimpleDateFormat date_fmt2 = new SimpleDateFormat("yyyy-MM-dd");

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

		// Date picker dialog is the pop up date picker
		final DatePickerDialog datePicker = new DatePickerDialog(this, 0,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {

						// The Gregorian calendar for storing dates
						Calendar cal = new GregorianCalendar(year, monthOfYear,
								dayOfMonth, 0, 0, 0);

						// If the dateType is 0 then it writes the text to the
						// start date
						// If it's 1 it writes to the end date
						if (dateType == 0) {
							NewTrip.this.startDate.setText(date_fmt.format(cal
									.getTime()));
							NewTrip.this.sqlStartDate = (date_fmt2.format(cal
									.getTime()));
						} else {
							NewTrip.this.endDate.setText(date_fmt.format(cal
									.getTime()));
							NewTrip.this.sqlEndDate = (date_fmt2.format(cal
									.getTime()));
						}

					}
				}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));

		// On click listener for the edit text
		startDate.setOnClickListener(new OnClickListener() {

			// This method is called when the start date is clicked
			@Override
			public void onClick(View v) {
				dateType = 0;
				datePicker.show();
			}
		});

		endDate.setOnClickListener(new OnClickListener() {

			// This method is called when the end date is clicked
			@Override
			public void onClick(View v) {
				dateType = 1;
				datePicker.show();
			}
		});

	}

	public void onClick(View v) {
		switch (v.getId()) {

		// If Cancel Button is clicked go back.
		case R.id.btnCancelNT:
			this.goBack();
			break;

		// If Save Button is clicked call saveTrip method
		case R.id.btnSaveNT:
			this.saveTrip(true);
			break;

		// If Packing List Button is clicked go to the pack list
		case R.id.btnPackingListNT:
			this.goToPackList();
			break;

		// If To Do List Button is clicked go to to do list
		case R.id.btnToDoListNT:
			this.goToToDoList();
			break;
		}

	}

	// Method to save data to SQLite database
	public void saveTrip(boolean isNew) {
		String msg = "All Fields Required";
		String msg2 = "Please choose a unique trip name";
		String name = tripName.getText().toString();
		String dest = destination.getText().toString();
		
		//Verifies that form is complete
		if (isComplete()) {
			NewTrip.this.activeTrip = new Trip(name, dest, sqlStartDate,
					sqlEndDate);

			//Verifies that this is a unique name
			if (helper.isUnique(activeTrip)) {
				helper.addTrip(activeTrip);
				if (isNew) {
					this.sendNotification(dest);
				}
				//When this is saved, the user is sent to the ViewTrip activity
				Intent intent = new Intent(this, ViewTrip.class);
				intent.putExtra("trip_name", activeTrip.getName());
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
			} else {
				Toast.makeText(NewTrip.this, msg2, Toast.LENGTH_LONG).show();
			}

		} else {
			Toast.makeText(NewTrip.this, msg, Toast.LENGTH_LONG).show();
		}
	}

	//checks to make sure that the form is complete
	public boolean isComplete() {
		boolean complete = false;
		String name = tripName.getText().toString();
		String dest = destination.getText().toString();
		String start = startDate.getText().toString();
		String end = endDate.getText().toString();

		if (!(name.equals("") || dest.equals("") || start.equals("") || end
				.equals(""))) {
			complete = true;
		}
		return complete;

	}

	//Returns the value of the start date formatted for sql
	public String getSQLStartDate() {
		return this.sqlStartDate;
	}

	//Returns the value of the end date formatted for sql
	public String getSQLEndDate() {
		return this.sqlEndDate;
	}

	//Before going to pack list, the trip is saved.
	public void goToPackList() {
		if (isComplete()) {
			this.saveTrip(false);
			Intent intent = new Intent(this, PackingList.class);
			intent.putExtra("trip_id",
					Integer.toString(NewTrip.this.activeTrip.getID()));
			startActivity(intent);
		} else {
			Toast.makeText(NewTrip.this, "All Fields Required",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	//Opens the to do list activity and pass the trip id with it
	public void goToToDoList() {
		if (isComplete()) {
			this.saveTrip(false);
			Intent intent = new Intent(this, ToDoListActivity.class);
			intent.putExtra("trip_id",
					Integer.toString(NewTrip.this.activeTrip.getID()));
			startActivity(intent);
		} else {
			Toast.makeText(NewTrip.this, "All Fields Required",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	//Builds a notification
	public void sendNotification(String dest) {

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);

		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher));
		builder.setContentTitle("New Trip Added");
		builder.setContentText("You're going to " + dest + "!");
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, builder.build());

	}
	
	//Handles going back
	public void goBack(){
		super.onBackPressed();
	}

}
