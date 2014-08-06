package com.arianaantonio.octopus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

public class MainActivity extends Activity {
	
	WebView webview;
	EditText urlField;
	Context context;
	
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
          
        WebSettings browserSettings = webview.getSettings();
        browserSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        
        Intent intent = getIntent();
        Uri data = intent.getData();
        
        Log.i("Main Activity", "Intent URL passed: " +data);
        if (data !=null) {
        	webview.loadUrl(data.toString());
        } else {
        	webview.loadUrl("http://www.google.com"); 
        }
        
        //webview.loadUrl("http://www.google.com");

        
        Button button = (Button) findViewById(R.id.button1);
        button.setBackgroundResource(R.drawable.right_circular128);
        button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String enteredText = urlField.getText().toString();
		        Log.i("Main Activity", "Entered URL: " +enteredText);
		        
		        //TODO Handle adding proper formatting to URL  
		        webview.loadUrl(enteredText);
			}
        	
        });
        
        bookmarkButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Save data to text file
				String saveURL = urlField.getText().toString();
				Log.i("Main Activity", "Bookmarked Link: " +saveURL);
				
				BufferedWriter writer = null;
				try {
					String filePath = context.getFilesDir().getPath().toString() + "/BookmarksFile.txt";
					File file = new File(filePath);
					System.out.println(file.getCanonicalPath());
					//file.delete();
					writer = new BufferedWriter(new FileWriter(file, true));
					String lineTitle = saveURL+ "\n";
					writer.write(lineTitle);

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
				Intent bookmarksActivity = new Intent(getBaseContext(), BookmarksActivity.class);
				startActivity(bookmarksActivity); 
				break;
		}
		return true;
	} 
    
}
