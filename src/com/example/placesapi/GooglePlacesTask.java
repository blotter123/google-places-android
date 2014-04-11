package com.example.placesapi;

import java.util.ArrayList;

import android.os.AsyncTask;

public class GooglePlacesTask extends AsyncTask<String, Void, String> {

	/*
	 * Example of an asynchronous task calling the GooglePlacesApi Interface
	 * Method to retrieve nearby restaurants - the list can then be used in a
	 * list view
	 */
	@Override
	protected String doInBackground(String... params) {

		double lat = 39.952818;
		double lng = -75.193565;
		double radius = 1000;
		ArrayList<Location> list = GooglePlacesAPI.getRestaurants(lat, lng,
				radius);
		System.out.println(list.size());
		return new String();
	}

}
