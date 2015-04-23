//This is for the pack list activity

package com.example.tripplanner;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ListActivity;

public class PackingList extends ListActivity {
	private SQLHelper helper;
	private SQLiteDatabase db;
	private static Trip activeTrip;
	private static PackList packList;
	private ArrayList<String> items = new ArrayList<String>();
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.packing_list);
		helper = new SQLHelper(this);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_checked, items);
		setListAdapter(adapter);

		// Receives trip id from prior activity and sets it to activeTrip
		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {
			activeTrip = helper.getTripByID(Integer.parseInt(extras
					.getString("trip_id")));
			packList = helper.getPackList(activeTrip);
		}

		// Dummy Code
		this.refreshList();
		this.saveList();
		/****************************************************************/
	}

	// Refreshes list and notifies adapter
	public void refreshList() {
		items.clear();

		for (TripListItem t : packList) {
			items.add(t.getText());
		}
		adapter.notifyDataSetChanged();

		int i = 0;
		for (TripListItem t : packList) {
			this.getListView().setItemChecked(i, t.isChecked());
			i++;
		}
	}

	// Allows checking and un-checking of items
	protected void onListItemClick(ListView l, View v, int position, long id) {
		CheckedTextView item = (CheckedTextView) v;
		if (item.isChecked()) {
			packList.get(position).setChecked();
		} else {
			packList.get(position).setUnChecked();
		}
	}

	// Writes list items to database
	public void saveList() {
		helper.saveList(packList);

	}

	protected void onPause() {
		super.onPause();
		this.saveList();
	}

}
