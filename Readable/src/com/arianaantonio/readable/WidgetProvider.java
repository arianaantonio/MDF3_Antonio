/*
 * Author: Ariana Antonio
 * 
 * Project: Readable
 * 
 * Package: com.arianaantonio.octopus
 * 
 * File: WidgetProvider.java
 * 
 * Purpose: The activity is the required provider for the widget. It supplies the essential widget functionality such as onUpdate.
 * onUpdate is called every hour and updates the book title to the next one in the book list. It also pulls the shared preferences 
 * so the name and font color are the ones the user configured on startup
 */            

package com.arianaantonio.readable;
 
import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;    
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {
	
	//global variables
	//String[] items;
	List<String> list = new ArrayList<String>();
	int count = 0;
	public static final String[] items = new String[]{"The Lord Of The Rings","If I Stay","Gone Girl", "Redshirts", "Ender's Game", "Stardust"};
	
	  
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		
		super.onDeleted(context, appWidgetIds);
	}
 
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, 
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		Log.i("Widget Provider" , "Count: " +count);
		
		//get preferences saved in configuration and set them to the widget
		SharedPreferences settings = context.getSharedPreferences("Settings", 0);
		String username = settings.getString("Username", "Stranger");
		String color = settings.getString("Color", "Gray");
		Log.i("Widget Provider", "Username and color: " +username+ " " +color);

        //items = list.toArray(items);
		Log.i("Widget Provider", "Book list in onUpdate: " +items[2]); 
		
		//check to make sure count isn't higher than array size, if so, start from the beginning
		if (count > items.length) {
			count = 0;
		}
		
		String bookTitle = items[count];
		Log.i("Widget Provider", "Title: " +bookTitle);
		Uri website; 
		if (bookTitle.equals("The Lord Of The Rings")) {
			website = Uri.parse("http://www.amazon.com/Lord-Rings-Fellowship-Towers-Return-ebook/dp/B007978OY6/ref=sr_1_1?ie=UTF8&qid=1408514449&sr=8-1&keywords=the+lord+of+the+rings");
		} else if (bookTitle.equals("If I Stay")) {
			website = Uri.parse("http://www.amazon.com/If-I-Stay-Gayle-Forman-ebook/dp/B0020BUWX2/ref=sr_1_1?s=books&ie=UTF8&qid=1408514492&sr=1-1&keywords=if+i+staym");
		} else if (bookTitle.equals("Gone Girl")) {
			website = Uri.parse("http://www.amazon.com/Gone-Girl-Gillian-Flynn-ebook/dp/B006LSZECO/ref=sr_1_1?s=books&ie=UTF8&qid=1408514517&sr=1-1&keywords=gone+girl");
		} else if (bookTitle.equals("Redshirts")) {
			website = Uri.parse("http://www.amazon.com/Redshirts-John-Scalzi-ebook/dp/B0079XPUOW/ref=sr_1_1?s=books&ie=UTF8&qid=1408514546&sr=1-1&keywords=redshirts");
		} else if (bookTitle.equals("Stardust")) { 
			website = Uri.parse("http://www.amazon.com/Stardust-Neil-Gaiman-ebook/dp/B000FC13Y0/ref=sr_1_1?s=books&ie=UTF8&qid=1408514575&sr=1-1&keywords=stardust");
		} else  {
			website = Uri.parse("http://www.amazon.com/Enders-Game-Ender-book-Saga-ebook/dp/B003G4W49C/ref=sr_1_1?s=books&ie=UTF8&qid=1408514600&sr=1-1&keywords=enders+game");
		}   
		
		//set the intent for launching the book url 
		Intent websiteIntent = new Intent(Intent.ACTION_VIEW, website);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, websiteIntent, 0);
		
		//set the widget text and colors
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		views.setOnClickPendingIntent(R.id.button1, pendingIntent);
		views.setTextViewText(R.id.nextBook, items[count]);
		views.setTextViewText(R.id.nextBookTitle, username+ "'s next book to read:");
		
		if (color.equals("Red")) {
			 views.setTextColor(R.id.nextBook, Color.RED);
	 	 } else if (color.equals("White")) {
	 		views.setTextColor(R.id.nextBook, Color.WHITE);
	 	 } else if (color.equals("Blue")) {
	 		views.setTextColor(R.id.nextBook, Color.BLUE); 
	 	 } else if (color.equals("Green")) {
	 		views.setTextColor(R.id.nextBook, Color.GREEN);
	 	 } else if (color.equals("Yellow")) {
	 		views.setTextColor(R.id.nextBook, Color.YELLOW); 
	 	 } else { 
	 		views.setTextColor(R.id.nextBook, Color.DKGRAY); 
	 	 }  
		count++;  
		//views.setTextViewText(R.id.nextBookTitle, "Your next book to read"); 
		
		//update the widget with the new information
		appWidgetManager.updateAppWidget(appWidgetIds, views);  
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		
	}

	 
}
