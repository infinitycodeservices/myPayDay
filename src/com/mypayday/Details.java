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


public class Details extends ListFragment implements OnClickListener {

	// Create array to store details
		ArrayList<Map<String, String>> detailsArray = new ArrayList<Map<String,  String>>();
		
	private ListView mListView;
	
	// Create JSON Parser object
	JSONParser jParser = new JSONParser();

	// Setting the URL for Agency by ID
	String url_search_checkDetails = "http://www.payproc.com/payroll/checkDetails.php";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		

		// Getting JSON string from URL
		JSONArray json = jParser.getJSONFromUrl(url_search_checkDetails, params);

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

		View rootView = inflater.inflate(R.layout.paystubdetails, container, false);

		// Set TextView variables to populate screen from detailsArray
		TextView cmpnyid = (TextView) rootView.findViewById(R.id.CmpnyID);
		TextView emplyid = (TextView) rootView.findViewById(R.id.EmplySSN);
		TextView ckdate = (TextView) rootView.findViewById(R.id.ckdate);
		TextView gross = (TextView) rootView.findViewById(R.id.gross);
		TextView fedwh = (TextView) rootView.findViewById(R.id.fedwh);
		TextView fica = (TextView) rootView.findViewById(R.id.fica);
		TextView medicare = (TextView) rootView.findViewById(R.id.medicare);
		TextView state = (TextView) rootView.findViewById(R.id.state);
		TextView sd = (TextView) rootView.findViewById(R.id.sd);
		TextView ckamnt = (TextView) rootView.findViewById(R.id.ckamnt);
		TextView directdep = (TextView) rootView.findViewById(R.id.directdep);

		// Fill TextViews from detailsArray
		cmpnyid.setText(detailsArray.get(0).get("CompanyID").toString());
		emplyid.setText(detailsArray.get(0).get("EmployeeID").toString());
		ckdate.setText(detailsArray.get(0).get("CheckDate").toString());
		gross.setText(detailsArray.get(0).get("Gross").toString());
		fedwh.setText(detailsArray.get(0).get("FederalWithHeld").toString());
		fica.setText(detailsArray.get(0).get("FICA").toString());
		medicare.setText(detailsArray.get(0).get("Medicare").toString());
		state.setText(detailsArray.get(0).get("State").toString());
		sd.setText(detailsArray.get(0).get("Short Term Disability").toString());
		ckamnt.setText(detailsArray.get(0).get("Check Amount").toString());
		directdep.setText(detailsArray.get(0).get("Direct Deposit").toString());
		
		
		mListView =(ListView)rootView.findViewById(android.R.id.list);
				

		return rootView;
	}

	@Override
	public void onClick(View v) {
		
		
		};
		
	}

