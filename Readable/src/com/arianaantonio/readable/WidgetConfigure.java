/*
 * Author: Ariana Antonio
 * 
 * Project: Readable
 * 
 * Package: com.arianaantonio.octopus
 * 
 * File: WidgetConfigure.java
 * 
 * Purpose: The activity launches when the widget is first added to the home screen. It gives the user the option to enter
 * their name to be displayed in the widget and change the book title font color. It saves those configurations to shared 
 * preferences and updates the widget
 */


package com.arianaantonio.readable;


import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Spinner;
 

public class WidgetConfigure extends Activity {
	
	ArrayList<String> arrayForSpinner = new ArrayList<String>();
	Context context;  
	String colorPicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;
		
		//create the spinner of font colors
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		arrayForSpinner.add("Red");
		arrayForSpinner.add("Green");
		arrayForSpinner.add("Gray");
		arrayForSpinner.add("Blue");
		arrayForSpinner.add("White");
		arrayForSpinner.add("Yellow");
		
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayForSpinner);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinnerAdapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				colorPicked = arrayForSpinner.get(position);
				Log.i("Main Activity", "Color Selected: " +colorPicked);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
			
		});
		
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Bundle extras = getIntent().getExtras();
				
				 if (extras != null) {
					 int widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
				
					 //get the widgetId and make sure it's valid
					 if (widgetID != AppWidgetManager.INVALID_APPWIDGET_ID) {
						
						 //get name entered and color picked and set them to the widget(remote view)
						 EditText userNameField = (EditText) findViewById(R.id.editText1);
						 String userName = userNameField.getText().toString();
						 Log.i("Main Activity", "Username: " +userName);
						
						 RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
						
						 view.setTextViewText(R.id.nextBook, "House Of Leaves");
						 if (userName.equals("")) {
							 view.setTextViewText(R.id.nextBookTitle, "Your next book to read:");
						 } else {
							 view.setTextViewText(R.id.nextBookTitle, userName+ "'s next book to read:" );
						 }
						 
						 if (colorPicked.equals("Red")) {
							 view.setTextColor(R.id.nextBook, Color.RED);
					 	 } else if (colorPicked.equals("White")) {
					 		view.setTextColor(R.id.nextBook, Color.WHITE);
					 	 } else if (colorPicked.equals("Blue")) {
					 		view.setTextColor(R.id.nextBook, Color.BLUE); 
					 	 } else if (colorPicked.equals("Green")) {
					 		view.setTextColor(R.id.nextBook, Color.GREEN);
					 	 } else if (colorPicked.equals("Yellow")) {
					 		view.setTextColor(R.id.nextBook, Color.YELLOW); 
					 	 } else {
					 		view.setTextColor(R.id.nextBook, Color.DKGRAY); 
					 	 }  
						 //save the name and color selection to shared preferences		
						 SharedPreferences settings = getSharedPreferences("Settings", 0);
						 SharedPreferences.Editor editor = settings.edit();
						 editor.putString("Username", userName);
						 editor.putString("Color", colorPicked);
						 editor.commit();
						 
						 //adding the book url to the button
						 Uri website = Uri.parse("http://www.amazon.com/House-Leaves-Mark-Z-Danielewski/dp/0375703764/ref=sr_1_1?s=books&ie=UTF8&qid=1408519554&sr=1-1&keywords=house+of+leaves");
						 Intent websiteIntent = new Intent(Intent.ACTION_VIEW, website);
						 
						 //launch url intent when button is clicked
						 PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, websiteIntent, 0);
						 view.setOnClickPendingIntent(R.id.button1, pendingIntent);
						 
						 //set intent for widget
						 Intent result = new Intent();
						 result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
						 setResult(RESULT_OK, result);
						 
						 //update the widget with configured information and add to home screen
						 AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
						 appWidgetManager.updateAppWidget(widgetID, view);
						 finish();  
						 
					 }
					 
				 }
				
			}
			
		});
	}
	
	

}
