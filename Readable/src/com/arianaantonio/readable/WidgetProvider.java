package com.arianaantonio.readable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	
	//String[] items;
	List<String> list = new ArrayList<String>();
	int count = 0;
	
	  
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, 
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	
		try {
			String filePath = context.getFilesDir().getPath().toString() + "/BookList.txt";
			File file = new File(filePath);
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;
		while ((line = br.readLine()) != null) {
			list.add(line);
		    Log.i("Widget Provider", "List item: " +line);
		}
		    br.close();
	    } catch (IOException e) {
			e.printStackTrace();
		}
		
		Log.i("Widget Provider" , "Count: " +count);
		
		SharedPreferences settings = context.getSharedPreferences("Settings", 0);
		String username = settings.getString("Settings", "Stranger");
		String color = settings.getString("Color", "Gray");
		Log.i("Widget Provider", "Username and color: " +username+ " " +color);
		
	
		
		String[] items = new String[list.size()];

        items = list.toArray(items);
		Log.i("Widget Provider", "Book list in onUpdate: " +items[2]); 
		
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
		
		 
		Intent websiteIntent = new Intent(Intent.ACTION_VIEW, website);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, websiteIntent, 0);
		
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
		views.setTextViewText(R.id.nextBookTitle, "Your next book to read"); 
		
		
		appWidgetManager.updateAppWidget(appWidgetIds, views);  
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		Log.i("Widget Provider", "Inside onEnabled");
		BufferedWriter writer = null;
		String filePath = context.getFilesDir().getPath().toString() + "/BookList.txt";
		File file = new File(filePath);
		file.delete();

				try {
					
					System.out.println(file.getCanonicalPath());
					//file.delete();
					writer = new BufferedWriter(new FileWriter(file, true));
					String lotr = "The Lord Of The Rings\n";
					String goneGirl = "Gone Girl\n";
					String ifIStay = "If I Stay\n";
					String redshirts = "Redshirts\n";
					String stardust = "Stardust\n";
					String endersGame = "Ender's Game\n";
				
					writer.write(lotr);
					writer.write(goneGirl); 
					writer.write(ifIStay);
					writer.write(redshirts);
					writer.write(stardust);
					writer.write(endersGame);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				try {
					
					
					
			        BufferedReader br = new BufferedReader(new FileReader(file));
			            String line;
			            while ((line = br.readLine()) != null) 
			            {
			                list.add(line);
			                Log.i("Widget Provider", "List item: " +line);
			            }
			            br.close();
					//String string;
					//StringBuffer stringBuffer = new StringBuffer();
					//while ((string = reader.readLine()) != null) {
					//stringBuffer.append(string + "\n");
					//}
					//favoritesView.setText(stringBuffer.toString());
					} catch (IOException e) {
					e.printStackTrace();
					}
	}

	 
}
