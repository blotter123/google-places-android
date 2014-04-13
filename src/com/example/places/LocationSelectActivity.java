package com.example.places;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.placesapi.GoogleLocation;
import com.example.placesapi.GooglePlacesTask;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

public class LocationSelectActivity extends Activity {

	public static final double DEFAULT_RADIUS = 1000;

	ArrayList<GoogleLocation> locations;

	class SetupListTask extends
			AsyncTask<Void, Void, ArrayList<GoogleLocation>> {
		private double latitude, longitude;

		public SetupListTask(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}

		@Override
		protected ArrayList<GoogleLocation> doInBackground(Void... params) {
			ArrayList<GoogleLocation> googleLocations = new ArrayList<GoogleLocation>();
			try {
				googleLocations = new GooglePlacesTask(this.latitude,
						this.longitude, DEFAULT_RADIUS).execute("").get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return googleLocations;
		};

		@Override
		protected void onPostExecute(ArrayList<GoogleLocation> googleLocations) {

			GoogleLocationListAdapter adapter = new GoogleLocationListAdapter(
					LocationSelectActivity.this,
					R.layout.google_location_list_item, googleLocations);

			final ListView listView = (ListView) findViewById(R.id.listNearbyPlaces);

			listView.setAdapter(adapter);

			listView.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Define Action when item is selected

				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Define Action when no item is selected

				}

			});
		};
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_select_activity);

		// Get Location
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		String provider = locationManager.getBestProvider(criteria, true);

		android.location.Location location = locationManager
				.getLastKnownLocation(provider);

		if (location != null) {

			try {
				this.locations = new SetupListTask(location.getLatitude(),
						location.getLongitude()).execute().get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("LocationSelectActivity.locations.length = "
					+ this.locations.size());
		}
	}

}
