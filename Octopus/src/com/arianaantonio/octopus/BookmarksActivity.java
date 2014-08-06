package com.arianaantonio.octopus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BookmarksActivity extends Activity {
	//global variables
	Context context;
	String string;
	String selectedItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmarks);
		context = this;
		
		//HashMap<String, String> displayText = new HashMap<String, String>();
		//ArrayList<HashMap<String, String>> myData = new ArrayList<HashMap<String, String>>();
		final ArrayList<String> myData = new ArrayList<String>();
		try {
			//reading saved file of favorites and setting to textView
			BufferedReader reader = new BufferedReader(new InputStreamReader(

			openFileInput("BookmarksFile.txt")));
			
			StringBuffer stringBuffer = new StringBuffer();
			while ((string = reader.readLine()) != null) {
				stringBuffer.append(string + "\n");
				//displayText.put("url", string);
				//myData.add(displayText);
				myData.add(string);
			}
				//favoritesView.setText(stringBuffer.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (context, android.R.layout.simple_list_item_1, myData);
		ListView listview = (ListView) findViewById(R.id.listView1);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				selectedItem = myData.get(position);
				Log.i("Bookmarks Activity", "Item selected: " +selectedItem);
				
				finish();
				
			}
			
		}); 
		
	}

	@Override
	public void finish() {
		Log.i("Bookmarks Activity", "Activity finished");

		Intent dataPassing = new Intent();
		dataPassing.putExtra("url", selectedItem);
		setResult(RESULT_OK, dataPassing);
		
		super.finish();
	}
	
}
