/*
 * Author: Ariana Antonio
 * 
 * Project: Octopus Browser
 * 
 * Package: com.arianaantonio.octopus
 * 
 * File: MainActivity.java
 * 
 * Purpose: This activity is the main activity and is launched when the application starts. It loads a website 
 * in a webview and allows the user to navigate the website and save it to bookmarks
 */

package com.arianaantonio.octopus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.arianaantonio.networkconnection.NetworkConnect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled") public class MainActivity extends Activity {
	
	//global variables
	WebView webview;
	EditText urlField;
	Context context;
	String urlPassed;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
        context = this;
        webview = (WebView) findViewById(R.id.webView1);
        urlField = (EditText) findViewById(R.id.editText1);
        Button bookmarkButton = (Button) findViewById(R.id.button2);
        bookmarkButton.setBackgroundResource(R.drawable.literature128);  
        urlField.setFocusableInTouchMode(true);
        urlField.requestFocus();
        
    	//checking network connection from JAR
        NetworkConnect networkConnection = new NetworkConnect();
        Boolean networkConn = networkConnection.connectionStatus(context);
        if (networkConn) {
        //Toast.makeText(context, "You are connected", Toast.LENGTH_LONG).show();
        } else {
        Toast.makeText(context, "You are NOT connected to the internet", Toast.LENGTH_LONG).show();
        }
           
        //setting the webview settings
        WebSettings browserSettings = webview.getSettings();
        browserSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        
        //get url passed from other app
        Intent intent = getIntent();
        Uri data = intent.getData();
        Log.i("Main Activity", "Intent URL passed: " +data);
        
        //check if data was passed, entered manually, or not at all
        if (data !=null) {
        	webview.loadUrl(data.toString());
        	urlField.setText(data.toString());
        } else if (urlPassed != null) {
        	webview.loadUrl(urlPassed);
        	urlField.setText(urlPassed);
        } else {
        	webview.loadUrl("http://www.google.com");
        	urlField.setText("http://www."); 
        }
        
        //button to go to website
        Button button = (Button) findViewById(R.id.button1);
        button.setBackgroundResource(R.drawable.right_circular128);
        button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String enteredText = urlField.getText().toString();
		        Log.i("Main Activity", "Entered URL: " +enteredText);
		        
		        Boolean checkURL = validURL(enteredText);
		        if (checkURL == true) {
		        	webview.loadUrl(enteredText);
		        } else {
		        	Toast.makeText(context, "Invalid URL. Please try again.", Toast.LENGTH_LONG).show();
		        }
			}
        	
        });
        
        //button to save a webpage as a bookmark
        bookmarkButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Save data to text file
				String saveURL = urlField.getText().toString();
				Log.i("Main Activity", "Bookmarked Link: " +saveURL);
				 
				//saving url to file
				BufferedWriter writer = null;
				try {
					String filePath = context.getFilesDir().getPath().toString() + "/BookmarksFile.txt";
					File file = new File(filePath);
					System.out.println(file.getCanonicalPath());
					//file.delete();
					writer = new BufferedWriter(new FileWriter(file, true));
					String lineTitle = saveURL+ "\n";
					writer.write(lineTitle);
					
					Toast.makeText(context, "Website saved as bookmark", Toast.LENGTH_LONG).show();

					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try { 
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				
			}
        	
        });
        
    }

    //inflate action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.action_bar, menu);
    	getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	//get which action bar item selected and perform action based on selection
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.refresh:
				Log.i("Main Activity", "Selected 'Refresh'");
				webview.reload();
				break;
			case R.id.backward:
				Log.i("Main Activity", "Selected 'Backward'");
				if (webview.canGoBack()) {
					webview.goBack();
				}
				break;
			case R.id.forward:
				Log.i("Main Activity", "Selected 'Forward'");
				if (webview.canGoForward()) {
					webview.goForward();
				}
				break;
			case R.id.bookmarks:
				Log.i("Main Activity", "Selected 'Booksmarks'");
				//launch bookmark activity and expect passed url on finish
				Intent bookmarksActivity = new Intent(getBaseContext(), BookmarksActivity.class);
				startActivityForResult(bookmarksActivity, 0); 
				break;
		}
		return true;
	
	}
	//test if entered URL is valid
	public boolean validURL(String urlTest) {
	    try {
	      @SuppressWarnings("unused")
		URL url = new URL(urlTest);
	      return true;
	    }
	    catch (MalformedURLException e) {  
	        return false;
	    }
	}
	//get returned url from BookmarksActivity
	protected void onActivityResult(int requestCode, int resultCode, Intent dataPassing) {
		Log.i("Main Activity", "Pulling passed data");

		if (resultCode == RESULT_OK && requestCode == 0) {
			if (dataPassing.hasExtra("url")) {
				urlPassed = dataPassing.getExtras().getString("url");
				webview.loadUrl(urlPassed);  
				urlField.setText(urlPassed);
				Log.i("Main Activity", "Passed URL: " +urlPassed);
			}
		}
	}
}