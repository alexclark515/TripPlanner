/**The PackList activity is called when the pack list is opened*/
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

	// Method called to set the list type (pack or to do) this is used for
	// writing to the database
	public void setList(Trip t) {
		tripList = helper.loadList(t, "pack");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.packing_list);
		list = (ListView) findViewById(android.R.id.list);

		// Permits context menu
		list.setLongClickable(true);
		registerForContextMenu(list);

		// Instantiates SQL Helper
		helper = new SQLHelper(this);

		// ArrayList adapter for list of packing items
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

		// Calls the method to refresh this list
		this.refreshList();
		/****************************************************************/
	}

	// Clears items in list, adds all items to list and notifies adapter
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

	// When the activity is paused, the list is saved
	protected void onPause() {
		this.saveList();
		super.onPause();

	}

	// When the activity is resumed, this retrieves the active trip id from the
	// database
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

	// Saves list when this activity is stopped
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

	// If the item in the text view is a new item, then a new item is created.
	// If the user is changing an item, it updates that item
	@Override
	public void onClick(View v) {
		if (isNewItem) {
			this.addNewItem();
		} else {
			this.changeItem();
		}
	}

	// Context menu for editing and deleting items
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == android.R.id.list) {
			acmi = (AdapterContextMenuInfo) menuInfo;
			menu.add("Delete");
			menu.add("Edit");

		}
	}

	// Handles context menu input
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);

		// Calls method to delete item at the acmi posistin
		if (item.getTitle().toString().equals("Delete")) {
			this.deleteItem(acmi.position);
		}
		// When the user wishes to edit an item, the text is placed in the edit
		// text, and the text on the button is changed to save, and the boolean
		// value of isNewItem is changed to false
		if (item.getTitle().toString().equals("Edit")) {
			input.setText(tripList.get(acmi.position).getText());
			btnAdd.setText("Save");
			selectedPos = acmi.position;
			isNewItem = false;
		}
		return false;
	}

	// Method used to delete an item. Removes it from list, saves the list
	// (writes it to the database), speaks the text, and refreshes the list
	public void deleteItem(int i) {
		tripList.remove(i);
		this.speak("item deleted");
		this.saveList();
		this.refreshList();
	}

	// Method to change an item. Gets the new text, speaks, "item updated",
	// resets the edit text, changes the ArrayList text, resets the button text
	// to "Add", resets the isNewItem to true, saves the list and refreshes it
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

	// Method to add new item. Speaks text, resets edit text, adds item to trip
	// list, saves to database, and refreshes list
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

	// Method to handle speaking of text
	public void speakText(String t) {
		if (speaker.isSpeaking()) {
			speaker.stop();
			speak(t);
		} else {
			speak(t);
		}
	}

}
