/**Object used to handle trip list items. I created a new class that extends the abstract class TripList, 
 * to make implementation more dynamic and scalable in case other lists will be added*/

package com.example.tripplanner;

public class ToDoList extends TripList {

	public ToDoList(Trip trip) {
		super(trip);
		super.setTable("todo");

	}

	// This method is used to make sure that the table it writes to is the to do
	// list
	public ToDoList() {
		super.setTable("todo");
	}
}
