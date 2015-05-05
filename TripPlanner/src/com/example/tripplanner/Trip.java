/**A Trip is the core Object of the TripPlanner Application it houses variables that define a Trip*/
package com.example.tripplanner;

public class Trip {
	private int tripID;
	private String tripName;
	private String tripDestination;
	private String dateStart;
	private String dateEnd;
	private PackList packList;
	private ToDoList toDoList;

	// Four Argument constructor for a trip requires a name, destination, start
	// date and end date
	public Trip(String tripName, String tripDestination, String dateStart,
			String dateEnd) {
		this.tripName = tripName;
		this.tripDestination = tripDestination;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

	// Single argument constructor for simplified implementation
	public Trip(String[] args) {
		this.setTripID(Integer.parseInt(args[0]));
		this.setName(args[1]);
		this.setDestination(args[2]);
		this.setDateStart(args[3]);
		this.setDateEnd(args[4]);
	}

	//Returns trip id
	public int getID() {
		return this.tripID;
	}

	//Returns trip name
	public String getName() {
		return this.tripName;
	}

	//Returns trip destination
	public String getDestination() {
		return this.tripDestination;
	}

	//Returns trip start date
	public String getDateStart() {
		return this.dateStart;
	}

	//Returns trip end date
	public String getDateEnd() {
		return this.dateEnd;
	}

	//Sets trip id
	public void setTripID(int id) {
		this.tripID = id;
	}

	//Sets trip name
	public void setName(String name) {
		this.tripName = name;
	}

	//Sets destination
	public void setDestination(String dest) {
		this.tripDestination = dest;
	}

	//Sets tart date
	public void setDateStart(String start) {
		this.dateStart = start;
	}

	//Sets end date
	public void setDateEnd(String end) {
		this.dateEnd = end;
	}

	//Sets pack list
	public void setPackList(PackList list) {
		this.packList = list;
	}

	//Sets to do list
	public void setToDoList(ToDoList list) {
		this.toDoList = list;
	}

	//Returns pack list
	public PackList getPackList() {
		return this.packList;
	}

	//Returns to do list
	public ToDoList getToDoList() {
		return this.toDoList;
	}
}
