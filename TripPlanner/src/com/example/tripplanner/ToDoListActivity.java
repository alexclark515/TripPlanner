/**The ToDoListActivity extends the PackingList (activity) in order to inherit all of it's methods*/
package com.example.tripplanner;

import android.os.Bundle;

public class ToDoListActivity extends PackingList {

	@Override
	public void setList(Trip t) {
		// Sets the trip list type to to do instead of pack
		super.tripList = super.helper.loadList(t, "todo");
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Changes title of activity
		super.title.setText("To Do List");
	}

}
