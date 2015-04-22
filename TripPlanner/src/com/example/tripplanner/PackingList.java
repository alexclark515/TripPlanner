//This is for the pack list activity

package com.example.tripplanner;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ListActivity;

public class PackingList extends ListActivity {
	private SQLHelper helper;
	private SQLiteDatabase db;
	private Trip activeTrip;
	private PackList packList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.packing_list);
		helper = new SQLHelper(this);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    activeTrip = helper.getTripByName(extras.getString("Trip"));
		}
		
		//Dummy Code
		packList = new PackList(activeTrip);
		TripListItem x = new TripListItem(packList, "Socks");
		TripListItem y = new TripListItem(packList, "Shirts");
		Toast.makeText(this, x.getText(), Toast.LENGTH_SHORT).show();
		/****************************************************************/
	}
	
	
}
