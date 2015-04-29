//To do list extends trip list, 

package com.example.tripplanner;

public class ToDoList extends TripList {

	public ToDoList(Trip trip) {
		super(trip);
		super.setTable("todo");

	}

	public ToDoList() {
		super.setTable("todo");
	}
}
