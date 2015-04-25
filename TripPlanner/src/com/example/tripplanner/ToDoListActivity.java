package com.example.tripplanner;

import android.os.Bundle;

public class ToDoListActivity extends PackingList{

	@Override
	public void setList(Trip t){
		super.tripList = super.helper.loadList(t, "todo");
	}
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		super.title.setText("To Do List");
	}

}
