/*
 * Author: Ariana Antonio
 * 
 * Project: Octopus Browser
 * 
 * Package: com.arianaantonio.octopus
 * 
 * File: BookmarksActivity.java
 * 
 * Purpose: This activity is launched when the user clicks the bookmark icon on the action bar in MainActivity. It displays
 * the bookmarked websites in a listview. When a website is clicked it sends the user back to the MainActivity view to display the 
 * web page in the webview
 */

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
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmarks);
		context = this;
	
		final ArrayList<String> myData = new ArrayList<String>();
		
		try {
			//reading saved file of favorites and adding to array
			BufferedReader reader = new BufferedReader(new InputStreamReader(

			openFileInput("BookmarksFile.txt")));
			
			StringBuffer stringBuffer = new StringBuffer();
			while ((string = reader.readLine()) != null) {
				stringBuffer.append(string + "\n");
				myData.add(string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//creating the listview and using the array of urls as the data
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (context, android.R.layout.simple_list_item_1, myData);
		ListView listview = (ListView) findViewById(R.id.listView1);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
	
				selectedItem = myData.get(position);
				Log.i("Bookmarks Activity", "Item selected: " +selectedItem);
				
				finish();
				
			}
			
		}); 
		
	}

	@Override
	public void finish() {
		Log.i("Bookmarks Activity", "Activity finished");

		//passing the selected url back to MainActivity
		Intent dataPassing = new Intent();
		dataPassing.putExtra("url", selectedItem);
		setResult(RESULT_OK, dataPassing);
		
		super.finish();
	}
	
}
