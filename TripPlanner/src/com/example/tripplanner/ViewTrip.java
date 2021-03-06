/**The View trip extends New Trip in order to inherit all of its methods*/
package com.example.tripplanner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewTrip extends NewTrip {
	final int PICK1 = Menu.FIRST + 1;
	final int PICK2 = Menu.FIRST + 2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Gets the trip name from prior intent and stores values to edit texts
		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {

			String tripName = extras.getString("trip_name");
			super.activeTrip = super.helper.getTripByName(tripName);
		}
		super.tripName.setText(super.activeTrip.getName());
		super.destination.setText(super.activeTrip.getDestination());
		super.startDate.setText(convertDate(super.activeTrip.getDateStart()));
		super.endDate.setText(convertDate(super.activeTrip.getDateEnd()));
		super.sqlStartDate = super.activeTrip.getDateStart();
		super.sqlEndDate = super.activeTrip.getDateEnd();

	}

	// This method overrides the NewTrip saveTrip method. It updates the
	// current trip instead of creating a new one
	@Override
	public void saveTrip(boolean toast) {
		String name = tripName.getText().toString();
		String dest = destination.getText().toString();

		super.activeTrip.setName(name);
		super.activeTrip.setDestination(dest);
		super.activeTrip.setDateStart(super.getSQLStartDate());
		super.activeTrip.setDateEnd(super.getSQLEndDate());
		super.helper.updateTrip(super.activeTrip);
		if (toast) {
			Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
		}

	}

	// Sends user to pack list and passes the trip id
	@Override
	public void goToPackList() {
		if (isComplete()) {
			this.saveTrip(false);
			Intent intent = new Intent(this, PackingList.class);
			intent.putExtra("trip_id",
					Integer.toString(super.activeTrip.getID()));
			startActivity(intent);
		} else {
			Toast.makeText(this, "All Fields Required", Toast.LENGTH_SHORT)
					.show();
		}
	}

	// This method converts the date in the database to a better looking
	// formated date
	public String convertDate(String dateString) {
		int year = Integer.parseInt(dateString.substring(0, 4));
		int month = Integer.parseInt(dateString.substring(5, 7)) - 1;
		int day = Integer.parseInt(dateString.substring(8, 10));
		Calendar cal = new GregorianCalendar(year, month, day, 0, 0, 0);
		return super.date_fmt.format(cal.getTime());
	}

	// Creates the Options Menu
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, PICK1, Menu.NONE, "Send Trip Details via Email");
		menu.add(0, PICK2, Menu.NONE, "Send Trip Details via SMS");

		return true;
	}

	// Handles the options menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		String name = super.activeTrip.getName();
		String dest = super.activeTrip.getDestination();
		String sD = super.activeTrip.getDateStart();
		String eD = super.activeTrip.getDateEnd();
		String content = "Here are the trip details.\n\n" + "Name: " + name
				+ "\n" + "Destination: " + dest + "\n" + "Start Date: " + sD
				+ "\n" + "End Date: " + eD + "\n\n" + "Cheers!";

		switch (itemId) {

		// Send Trip details via Email
		case PICK1:
			Intent email = new Intent(Intent.ACTION_SEND);
			email.setType("message/rfc822");

			email.putExtra(Intent.EXTRA_SUBJECT, name + " Trip Details");
			email.putExtra(Intent.EXTRA_TEXT, content);

			startActivity(Intent
					.createChooser(email, "Choose an Email Client:"));

			return true;

			// Send Trip details via SMS
		case PICK2:
			Intent sms = new Intent(Intent.ACTION_VIEW);
			sms.setType("vnd.android-dir/mms-sms");
			sms.putExtra("sms_body", content);

			startActivity(sms);

			return true;
		}
		return false;
	}

	// When you go back, you return to main screen instead of NewTrip Activity
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Intent i = new Intent(this, MainActivity.class);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(i);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// Handles going back
	@Override
	public void goBack() {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

}
