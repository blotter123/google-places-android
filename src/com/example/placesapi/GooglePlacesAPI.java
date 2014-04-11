package com.example.placesapi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GooglePlacesAPI {

	private static final String GOOGLE_PLACES_API_KEY = "AIzaSyDdD_L2tLE74huHBOFSYOaYPFHEIS_Pv8U";
	private static final String GOOGLE_PLACES_API_URL = "https://maps.googleapis.com/maps/api/place/search/json?";

	/*
	 * Public Interface method to retrieve a list of Restaurant Locations (20 closest)
	 * given lat/lng/radius
	 */
	public static ArrayList<Location> getRestaurants(double lat, double lng,
			double radius) {

		// form request url based on parameters
		String reqUrl = ApiRequestBuilder.getRequestObject(lat, lng, radius,
				GOOGLE_PLACES_API_KEY, GOOGLE_PLACES_API_URL);

		// get the JSON response from Google Places API
		JSONObject resp = ApiConnection.getReponse(reqUrl);

		// form the Location list of nearby restaurants
		ArrayList<Location> results = parseGooglePlaceResponse(resp);

		return results;
	}

	private static ArrayList<Location> parseGooglePlaceResponse(
			JSONObject jObject) {
		ArrayList<Location> answer = new ArrayList<Location>();

		try {
			JSONArray resultsArray = (JSONArray) jObject
					.getJSONArray("results");
			System.out.println("resultsArray legnth: " + resultsArray.length());
			for (int i = 0; i < resultsArray.length(); i++) {
				answer.add(new Location(resultsArray.getJSONObject(i)));
				System.out.println(resultsArray.getJSONObject(i).toString());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return answer;

	}
}
