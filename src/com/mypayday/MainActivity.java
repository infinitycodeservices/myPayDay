package com.mypayday;

/**
 * Created by Infinity Code Services on 12/17/2014.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.mypayday.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	Button btnSingIn;
    EditText pinBox0;
    EditText pinBox1;
    EditText pinBox2;
    EditText compID0;
    TextView CmpnyID;
    TextView EmplySSN;
    TextView CkDate;
    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    
    public void agencySearch(String tsearch)	{
		// Setting the URL for the Search by Check
		String url_search_checkDetails = "http://www.infinitycodeservices.com/checkSearch.php";
		// Building parameters for the search
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SSN", tsearch));

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
				resultsList.add(map);

			}
			catch (JSONException e) {
				e.printStackTrace();

			}

		};

		MainActivity.setResultsList(resultsList);

	}
	
	// array to store search results for use in multiple fragments
		protected static ArrayList<Map<String, String>> resultsList = null;
	
	public static ArrayList<Map<String, String>> getResultsList()	{
		return resultsList;
	}
	
	public static void setResultsList(ArrayList<Map<String, String>> arrList)	{
		MainActivity.resultsList = arrList;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSingIn = (Button)findViewById(R.id.btnSingIn);
        compID0 = (EditText)findViewById(R.id.compID0);
        pinBox0 = (EditText)findViewById(R.id.pinBox0);
        pinBox1 = (EditText)findViewById(R.id.pinBox1);
        pinBox2 = (EditText)findViewById(R.id.pinBox2);
        CmpnyID = (TextView)findViewById(R.id.CmpnyID);
        EmplySSN = (TextView)findViewById(R.id.EmplySSN);
        
       


        btnSingIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(MainActivity.this, "Details.java",
                        "Validating user...", true);
                new Thread(new Runnable() {
                    public void run() {
                        login();
                    }
                }).start();
            }
        });
    }

    void login(){
        try{

            httpclient=new DefaultHttpClient();
            httppost= new HttpPost("http://payproc.net/payroll/checkSearch.php"); // make sure the url is correct.
            //add your data
            nameValuePairs = new ArrayList<NameValuePair>(2);
            // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
            nameValuePairs.add(new BasicNameValuePair("compID0",compID0.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
            nameValuePairs.add(new BasicNameValuePair("pinBox0",pinBox0.getText().toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("pinBox1",pinBox1.getText().toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("pinBox2",pinBox2.getText().toString().trim()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //Execute HTTP Post Request
            response=httpclient.execute(httppost);
            // edited by James from coderzheaven.. from here....
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + response);
            runOnUiThread(new Runnable() {
                public void run() {
                	CmpnyID.setText("Response from PHP : " + response);
                	EmplySSN.setText("Response from PHP : " + response);
                    dialog.dismiss();
                }
            });

            if(response.equalsIgnoreCase("User Found")){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this,"Login Success", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(MainActivity.this, Details.class));
            }else{
                showAlert();
            }

        }catch(Exception e){
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
    public void showAlert(){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Login Error.");
                builder.setMessage("CompanyID or LoginID is incorrect")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    
    
        
}
}