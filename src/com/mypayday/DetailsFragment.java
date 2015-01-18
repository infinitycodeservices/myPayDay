package com.mypayday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mypayday.MainActivity;
import com.mypayday.R;
import com.mypayday.JSONParser;

//This is a test

public class DetailsFragment extends ListFragment implements OnClickListener {

	// Create array to store details
	ArrayList<Map<String, String>> detailsArray = new ArrayList<Map<String,  String>>();

	// Create array to store comments
	ArrayList<Map<String, String>> commentsArray = new ArrayList<Map<String,  String>>();
	
	//Set AgencyID for comments selection
	String comments = MainActivity.getDetailsID();
		
	private Button btnComment;
	
	private Button btnFavorite;
	
	private ListView mListView;
	
	// Create JSON Parser object
	JSONParser jParser = new JSONParser();

	// Setting the URL for Agency by ID
	String url_search_agency = "http://www.infinitycodeservices.com/get_agency_by_id.php";
	
	// Setting the URL for the Select comments
	String url_search_comments = "http://www.infinitycodeservices.com/get_feedback.php";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// Building parameters for the search
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("AgencyID", comments));

		// Getting JSON string from URL
		JSONArray json = jParser.getJSONFromUrl(url_search_agency, params);

		for (int i = 0; i < json.length(); i++)	{
			HashMap<String, String> map = new HashMap<String, String>();

			try	{
				JSONObject c = (JSONObject) json.get(i);
				//Fill map
				Iterator iter = c.keys();
				while(iter.hasNext())	{
					String currentKey = (String) iter.next();
					map.put(currentKey, c.getString(currentKey));
				}
				detailsArray.add(map);

			}
			catch (JSONException e) {
				e.printStackTrace();

			}
		}

		View rootView = inflater.inflate(R.layout.fragment_details, container, false);

		// Set TextView variables to populate screen from detailsArray
		TextView agname = (TextView) rootView.findViewById(R.id.agencynm);
		TextView agaddr = (TextView) rootView.findViewById(R.id.address);
		TextView agcity = (TextView) rootView.findViewById(R.id.city);
		TextView agstate = (TextView) rootView.findViewById(R.id.state);
		TextView agzip = (TextView) rootView.findViewById(R.id.zip);
		TextView agphone = (TextView) rootView.findViewById(R.id.phone);
		TextView agcontact = (TextView) rootView.findViewById(R.id.contact);
		TextView agages = (TextView) rootView.findViewById(R.id.ages);
		TextView agcapty = (TextView) rootView.findViewById(R.id.capacity);

		// Fill TextViews from detailsArray
		agname.setText(detailsArray.get(0).get("AgencyName").toString());
		agaddr.setText(detailsArray.get(0).get("Address1").toString());
		agcity.setText(detailsArray.get(0).get("City").toString());
		agstate.setText(detailsArray.get(0).get("State").toString());
		agzip.setText(detailsArray.get(0).get("Zip").toString());
		agphone.setText(detailsArray.get(0).get("Phone").toString());
		agcontact.setText(detailsArray.get(0).get("ContactFN").toString() + " " + 
				detailsArray.get(0).get("ContactLN").toString());
		agages.setText(detailsArray.get(0).get("MinAge").toString() + " - " +
				detailsArray.get(0).get("MaxAge").toString());
		agcapty.setText(detailsArray.get(0).get("Capacity").toString());
		
		
		mListView =(ListView)rootView.findViewById(android.R.id.list);
				

		return rootView;
	}

	@Override
	public void onClick(View v) {
		//SharedPreferences ui = getActivity().getPreferences(Context.MODE_PRIVATE);
		
		
		};
		
	}

