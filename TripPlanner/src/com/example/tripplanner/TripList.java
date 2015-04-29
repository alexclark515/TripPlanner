package com.example.tripplanner;

import java.util.ArrayList;

public abstract class TripList extends ArrayList<TripListItem> {

	private static final long serialVersionUID = 1L;
	private Trip trip;
	private String table;

	public TripList(Trip trip) {
		this.trip = trip;
	}

	public TripList() {
	}
	
	public void addItem(TripListItem t){
		this.add(t);
		t.setTable(this.table);
		t.setTrip(this.trip);
		t.setTripList(this);
	}
	
	public void unCheckAll(){
		for (TripListItem t : this){
			t.setUnChecked();
		}
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getTable() {
		return this.table;
	}

	public int getTripID() {
		return this.trip.getID();
	}

	public Trip getTrip() {
		return this.trip;
	}
	
	public void setTrip(Trip trip){
		this.trip = trip;
	}

}
