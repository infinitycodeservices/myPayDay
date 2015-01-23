package com.mypayday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mypayday.MainActivity;
import com.mypayday.R;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

//Ths is a test

public class Results extends ListFragment {

	private OnFragmentInteractionListener mListener;
	
	ArrayList<Map<String, String>> resultsList = new ArrayList<Map<String, String>>();
	
	// Setup ArrayList<Map> to populate listView
	List<Map> data = new ArrayList<Map>();
	
	private ListView mListView;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//Get search results from stored ArrayList
		resultsList = MainActivity.getResultsList();
				
		// Load required fields from resultsList
		for (int i = 0; i < resultsList.size(); i++)	{
			Map dmap = new HashMap();
			dmap.put("agencyID", resultsList.get(i).get("AgencyID"));
			dmap.put("agencyName", resultsList.get(i).get("AgencyName"));
			dmap.put("address", resultsList.get(i).get("Address1") + " " + 
					resultsList.get(i).get("City") + " " + resultsList.get(i).get("State") + ", " +
					resultsList.get(i).get("Zip"));
			data.add(dmap);
		}

		View rootView = inflater.inflate(R.layout.fragment_results, container, false);

		//Build listView from results
		mListView =(ListView)rootView.findViewById(android.R.id.list);


		// updating listview
		setListAdapter(getListAdapter());


		return rootView;
		
	}
	
	
	
	public void onListFragmentItemClick(int position)	{
		
	}
	

}
