package com.example.tripplanner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

public class Alerts extends Activity {
	private TextView textView;
	private Calendar cal = Calendar.getInstance();
	private SimpleDateFormat date_fmt = new SimpleDateFormat("EEE, MMM d, yyyy");
	private DatePickerDialog datePicker;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notify);
		this.textView = (TextView) findViewById(R.id.alert_date);
		this.datePickLogic();
		textView.setOnClickListener(new OnClickListener() {

			// This method is called when the edit text is clicked
			@Override
			public void onClick(View v) {
				datePicker.show();
			}
		});
	}

	public void datePickLogic() {
		datePicker = new DatePickerDialog(this, 0,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {

						Calendar cal = new GregorianCalendar(year, monthOfYear,
								dayOfMonth, 0, 0, 0);
							Alerts.this.textView.setText(date_fmt.format(cal
									.getTime()));
					
					}}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
	}

}
