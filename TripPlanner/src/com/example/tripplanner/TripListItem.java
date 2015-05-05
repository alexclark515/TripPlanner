/**A trip list item is an item on a to do list or a packing list
//There is always a trip associated with a trip to make sure the
trip id is written to the database*/

package com.example.tripplanner;

public class TripListItem {
	private boolean checked = false;
	private String text;
	private Trip trip;
	private TripList list;
	private String table;
	private int Index;

	// One argument constructor is just the text of the item
	public TripListItem(String text) {
		this.text = text;
	}

	// Two argument constructor is the text and the checked value (true or
	// false)
	public TripListItem(String text, boolean checked) {
		this(text);
		this.checked = checked;
	}

	// Checks this item
	public void setChecked() {
		this.checked = true;
	}

	// Sets the trip list of this item
	public void setTripList(TripList t) {
		this.list = t;
	}

	// Un-checks this item
	public void setUnChecked() {
		this.checked = false;
	}

	// Returns the trip list of this item
	public TripList getTripList() {
		return this.list;
	}

	// Sets the index of this item
	public void setIndex(int index) {
		this.Index = index;
	}

	// Sets the text
	public void setText(String s) {
		this.text = s;
	}

	// Sets the table
	public void setTable(String table) {
		this.table = table;
	}

	// Returns the table name
	public String getTable() {
		return this.table;
	}

	// Gets the text of this item
	public String getText() {
		return this.text;
	}

	// Returns trip id of the Trip associated with this item
	public int getTripID() {
		return this.trip.getID();
	}

	// To String method for a trip list item
	public String toString() {
		return this.getTripID() + " " + this.getTable() + " " + this.text;
	}

	// Returns the index value of this item
	public int getIndex() {
		return this.Index;
	}

	// Returns true if the item is checked
	public boolean isChecked() {
		return this.checked;
	}

	// Sets the trip of this item
	public void setTrip(Trip t) {
		this.trip = t;
	}

}
