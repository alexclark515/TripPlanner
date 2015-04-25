//This is for the pack list activity

package com.example.tripplanner;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.InputType;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ListActivity;

public class PackingList extends ListActivity implements OnClickListener,
		OnInitListener {
	protected SQLHelper helper;
	private SQLiteDatabase db;
	private static Trip activeTrip;
	protected static TripList tripList;
	private ArrayList<String> items = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private EditText input;
	private TextToSpeech speaker;
	private Button btnAdd;
	private AdapterView.AdapterContextMenuInfo acmi;
	private int selectedPos;
	protected Bundle extras;
	protected TextView title;
	private boolean isNewItem = true;
	ListView list;

	public void setList(Trip t) {
		tripList = helper.loadList(t, "pack");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.packing_list);
		list = (ListView) findViewById(android.R.id.list);
		list.setLongClickable(true);
		registerForContextMenu(list);
		helper = new SQLHelper(this);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_checked, items);
		setListAdapter(adapter);
		input = (EditText) findViewById(R.id.packingListInput);
		btnAdd = (Button) findViewById(R.id.btnPackAdd);
		btnAdd.setOnClickListener(this);
		title = (TextView) findViewById(R.id.packingListViewName);

		// Initialize Speech Engine (context, listener object)
		speaker = new TextToSpeech(this, this);

		// Receives trip id from prior activity and sets it to activeTrip
		extras = this.getIntent().getExtras();
		if (extras != null) {
			activeTrip = helper.getTripByID(Integer.parseInt(extras
					.getString("trip_id")));
			this.setList(activeTrip);
		}

		// Dummy Code
		this.refreshList();
		/****************************************************************/
	}

	// Refreshes list and notifies adapter
	public void refreshList() {
		items.clear();
		for (int i = 0; i < tripList.size(); i++) {
			items.add(tripList.get(i).getText());
			if (tripList.get(i).isChecked()) {
				list.setItemChecked(i, true);
			}
		}

		adapter.notifyDataSetChanged();
	}

	// Allows checking and un-checking of items
	protected void onListItemClick(ListView l, View v, int position, long id) {

		CheckedTextView item = (CheckedTextView) v;
		if (item.isChecked()) {
			tripList.get(position).setChecked();
			this.saveList();
		} else {
			tripList.get(position).setUnChecked();
			this.saveList();
		}

		this.refreshList();

	}

	// Writes list items to database
	public void saveList() {
		tripList.unCheckAll();
		SparseBooleanArray checked = list.getCheckedItemPositions();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (checked.get(i)) {
				tripList.get(i).setChecked();
			}
			helper.saveList(tripList);
		}
	}

	protected void onPause() {
		this.saveList();
		super.onPause();

	}

	protected void onResume() {
		super.onResume();
		extras = this.getIntent().getExtras();
		if (extras != null) {
			activeTrip = helper.getTripByID(Integer.parseInt(extras
					.getString("trip_id")));
			this.setList(activeTrip);
		}
		this.refreshList();
	}

	protected void onStop() {
		this.saveList();
		super.onStop();
	}

	@Override
	public void onInit(int status) {
		// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
		if (status == TextToSpeech.SUCCESS) {
			// Set preferred language to US english.
			// If a language is not be available, the result will indicate it.
			int result = speaker.setLanguage(Locale.UK);

			// int result = speaker.setLanguage(Locale.FRANCE);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Language data is missing or the language is not supported.
				Log.e("error", "Language is not available.");
			} else {
				// The TTS engine has been successfully initialized
				Log.i("error", "TTS Initialization successful.");
			}
		} else {
			// Initialization failed.
			Log.e("error", "Could not initialize TextToSpeech.");
		}

	}

	// On destroy
	public void onDestroy() {

		// Shut down TTS engine
		if (speaker != null) {
			speaker.stop();
			speaker.shutdown();
		}
		super.onDestroy();
	}

	// Speaks the contents of output
	@SuppressWarnings("deprecation")
	public void speak(String output) {
		speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public void onClick(View v) {
		if (isNewItem) {
			this.addNewItem();
		} else {
			this.changeItem();
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == android.R.id.list) {
			acmi = (AdapterContextMenuInfo) menuInfo;
			menu.add("Delete");
			menu.add("Edit");

		}
	}

	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);

		if (item.getTitle().toString().equals("Delete")) {
			this.deleteItem(acmi.position);
		}
		if (item.getTitle().toString().equals("Edit")) {
			input.setText(tripList.get(acmi.position).getText());
			btnAdd.setText("Save");
			selectedPos = acmi.position;
			isNewItem = false;
		}
		return false;
	}

	public void deleteItem(int i) {
		tripList.remove(i);
		this.speak("item deleted");
		this.saveList();
		this.refreshList();
	}

	public void changeItem() {
		String newItem = input.getText().toString();
		this.speakText("item updated");
		input.setText("");
		tripList.get(selectedPos).setText(newItem);
		btnAdd.setText("Add");
		isNewItem = true;
		this.saveList();
		this.refreshList();
	}

	public void addNewItem() {
		String newItem = input.getText().toString();
		if (!(newItem.equals(""))) {
			this.speak(newItem + " added");
			input.setText("");
			tripList.addItem(new TripListItem(newItem));
			this.saveList();
			this.refreshList();
		}
	}

	public void speakText(String t) {
		if (speaker.isSpeaking()) {
			speaker.stop();
			speak(t);
		} else {
			speak(t);
		}
	}

}
