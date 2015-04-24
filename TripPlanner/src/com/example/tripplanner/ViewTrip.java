package com.example.tripplanner;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

public class ViewTrip extends NewTrip {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
		int month = Integer.parseInt(dateString.substring(5, 7));
		int day = Integer.parseInt(dateString.substring(8, 10));
		Calendar cal = new GregorianCalendar(year, month, day, 0, 0, 0);
		return super.date_fmt.format(cal.getTime());
	}

}
