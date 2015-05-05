/**Object used to handle trip list items. I created a new class that extends the abstract class TripList, 
 * to make implementation more dynamic and scalable in case other lists will be added
 * Both PackList and ToDoList extend this class*/

package com.example.tripplanner;

import java.util.ArrayList;

//A trip list is an array list of trip list items associated to a specific trip
public abstract class TripList extends ArrayList<TripListItem> {

	private static final long serialVersionUID = 1L;
	private Trip trip;
	private String table;

	public TripList(Trip trip) {
		this.trip = trip;
	}

	//0-argument constructor
	public TripList() {
	}

	//Handles the adding of items to this list
	public void addItem(TripListItem t) {
		this.add(t);
		t.setTable(this.table);
		t.setTrip(this.trip);
		t.setTripList(this);
	}

	//Un-checks all items in this list
	public void unCheckAll() {
		for (TripListItem t : this) {
			t.setUnChecked();
		}
	}

	//Associates this list to a database table
	public void setTable(String table) {
		this.table = table;
	}

	//Returns the name of the table of this list
	public String getTable() {
		return this.table;
	}

	//Returns the id of the trip associated to this list
	public int getTripID() {
		return this.trip.getID();
	}

	//Returns the Trip object associate to this list
	public Trip getTrip() {
		return this.trip;
	}

	//Associates this list to a Trip object
	public void setTrip(Trip trip) {
		this.trip = trip;
	}

}
