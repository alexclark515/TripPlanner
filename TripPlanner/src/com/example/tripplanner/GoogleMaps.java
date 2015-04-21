package com.example.tripplanner;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class GoogleMaps extends Activity {

	private GoogleMap myMap;
	private static final float zoom = 8.0f;
	private Geocoder gc;
	private double lat;
	private double lng;
	private EditText address;
	private Button btnSearch;
	private LatLng position = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_maps);
		
		btnSearch = (Button)findViewById(R.id.myBtnSearch);
		address = (EditText)findViewById(R.id.myAddress);
		
		// set up GoogleMap
		myMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		myMap.setMyLocationEnabled(true);
		myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.345017, -71.100380), zoom));
		
		//set up Geocoder
		gc = new Geocoder(this);
		
		// click on map to get the location
		myMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng point) {
				myMap.addMarker(new MarkerOptions()
					.position(point)
					.draggable(true));
			}
		});
		
		// click button to get location
		btnSearch.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String addressInput = address.getText().toString(); 
				try {
					List<Address> FoundAddresses = gc.getFromLocationName(
							addressInput, 1); // get locations

					if (FoundAddresses.size() == 0) 
						showInvalidAddressMsg();						
					 else {
						showListOfFoundAddresses(FoundAddresses);
						
						//position at the first address from the list
						position = new LatLng(FoundAddresses.get(0).getLatitude(),FoundAddresses.get(0).getLongitude());
						myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 17.0f));
					   }
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
				}

			}// onClick
		}); // btnSearch
	}
	
	// use button to get location
	// alert dialog if invalid address
	private void showInvalidAddressMsg() {
		Dialog locationError = new AlertDialog.Builder(
				GoogleMaps.this).setIcon(0).setTitle(
				"Error").setPositiveButton("OK", null)
				.setMessage("Sorry, your address doesn't exist.")
				.create();
		locationError.show();
	}// showInvalidAddressMsg

	//put first locations on a Toast
	private void showListOfFoundAddresses (List<Address> foundAddresses){
		String msg = "";
		for (int i = 0; i < foundAddresses.size(); i++) {
			// show results as address, Longitude and Latitude
			Address a = foundAddresses.get(i);
			lat = a.getLatitude();
			lng = a.getLongitude();
			String adr = "\n" + a.getAddressLine(0) 
			           + "\n" + a.getAddressLine(1);
			msg += lat + " " + lng + adr;	
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();		
		}
	}// showListOfFoundAddresses
	
}
