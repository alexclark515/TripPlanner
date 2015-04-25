package com.example.tripplanner;

public class TripListItem {
	private boolean checked = false;
	private String text;
	private Trip trip;
	private TripList list;
	private String table;
	private int Index;

	public TripListItem(String text) {
		this.text = text;
	}
	
	public TripListItem(String text, boolean checked){
		this(text);
		this.checked = checked;
	}

	public void setChecked() {
		this.checked = true;
	}
	
	public void setTripList(TripList t){
		this.list = t;
	}
	
	public void setUnChecked(){
		this.checked = false;
	}

	public TripList getTripList() {
		return this.list;
	}
	
	public void setIndex(int index){
		this.Index = index;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getTable() {
		return this.table;
	}
	
	public String getText(){
		return this.text;
	}
	
	public int getTripID(){
		return this.trip.getID();
	}
	
	public String toString(){
		return this.getTripID() + " " + this.getTable() + " " + this.text;
	}
	
	public int getIndex(){
		return this.Index;
	}
	
	public boolean isChecked(){
		return this.checked;
	} 
	
	public void setTrip(Trip t){
		this.trip = t;
	}
	
}
