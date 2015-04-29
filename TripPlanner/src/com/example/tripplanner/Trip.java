package com.example.tripplanner;


public class Trip {
	private int tripID;
	private String tripName;
	private String tripDestination;
	private String dateStart;
	private String dateEnd;
	private PackList packList;
	private ToDoList toDoList;

	public Trip(String tripName, String tripDestination, String dateStart, String dateEnd) {
		this.tripName = tripName;
		this.tripDestination = tripDestination;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}
	
	public Trip(String[] args){
		this.setTripID(Integer.parseInt(args[0]));
		this.setName(args[1]);
		this.setDestination(args[2]);
		this.setDateStart(args[3]);
		this.setDateEnd(args[4]);
	}
	
	public int getID(){
		return this.tripID;
	}
	
	public String getName(){
		return this.tripName;
	}
	
	public String getDestination(){
		return this.tripDestination;
	}
	
	public String getDateStart(){
		return this.dateStart;
	}
	
	public String getDateEnd(){
		return this.dateEnd;
	}
	
	public void setTripID(int id){
		this.tripID = id;
	}
	
	public void setName(String name){
		this.tripName = name;
	}
	
	public void setDestination(String dest){
		this.tripDestination = dest;
	}
	
	public void setDateStart(String start){
		this.dateStart = start;
	}
	
	public void setDateEnd(String end){
		this.dateEnd = end;
	}
	
	public void setPackList(PackList list){
		this.packList = list;
	}
	
	public void setToDoList(ToDoList list){
		this.toDoList = list;
	}
	
	public PackList getPackList(){
		return this.packList;
	}
	
	public ToDoList getToDoList(){
		return this.toDoList;
	}
}
