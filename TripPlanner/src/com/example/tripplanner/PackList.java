/**Object used to handle trip list items. I created a new class that extends the abstract class TripList, 
 * to make implementation more dynamic and scalable in case other lists will be added*/
package com.example.tripplanner;

public class PackList extends TripList {

	public PackList(Trip trip) {
		super(trip);
		super.setTable("pack");
		;
	}

	// This method is used to make sure that the table it writes to is the pack
	// list
	public PackList() {
		super.setTable("pack");
	}

}
