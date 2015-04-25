//This is a pack list class not activity

package com.example.tripplanner;

public class PackList extends TripList {
	
	public PackList(Trip trip) {
		super(trip);
		super.setTable("pack");;
	}
	
	public PackList(){
		super.setTable("pack");
	}

}
