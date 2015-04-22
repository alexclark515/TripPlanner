package com.example.tripplanner;

public class TripListItem {
	private boolean checked = false;
	private String text;
	private Trip trip;
	private TripList list;
	private String table;
	private int Index;

	public TripListItem(TripList list, String text) {
		this.list = list;
		this.text = text;
		this.table = list.getTable();
		this.trip = list.getTrip();
		list.add(this);
	}
	
	public TripListItem(TripList list, String text, boolean checked){
		this(list, text);
		this.checked = checked;
	}

	public void setChecked() {
		this.checked = true;
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
	
}
