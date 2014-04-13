package com.example.places;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.placesapi.GoogleLocation;
import com.example.placesapi.GooglePlacesTask;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationSelectActivity extends Activity {
	
	public static final double DEFAULT_RADIUS = 1000;

	ArrayList<GoogleLocation> locations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_select_activity);
		
		//Get Location
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		String provider = locationManager.getBestProvider(criteria, true);

		// LocationManager locationManager = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);
		android.location.Location location = locationManager.getLastKnownLocation(provider);
		
		if(location != null){
			
			try {
				this.locations = new GooglePlacesTask(location.getLatitude(),location.getLongitude(),DEFAULT_RADIUS).execute("").get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("LocationSelectActivity.locations.length = " + this.locations.size());
		}
	}
	

}
