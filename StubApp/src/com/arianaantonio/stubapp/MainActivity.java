/* Ariana Antonio
 * August 5, 2014
 * Full Sail University, Mobile Development
 * MDF3
 * Purpose: To provide an app that will call the Octopus browser app
 */ 
package com.arianaantonio.stubapp;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	EditText url;
	Button button;
	Context mContext;
	String enteredURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        url = (EditText) findViewById(R.id.editText1);
        
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				enteredURL = (String) url.getText().toString();
				Log.i("Main Activity", "Entered URL: " +enteredURL);

				Boolean checkURL = validURL(enteredURL);
				if (checkURL == true) { 
					//Log.i("Main Activity", "Valid url"); 
					Toast.makeText(mContext, "Valid url", Toast.LENGTH_LONG).show();
					Uri webUri = Uri.parse(enteredURL);
					Intent intent = new Intent(Intent.ACTION_VIEW, webUri);
					startActivity(intent);
					
				} else {
					Toast.makeText(mContext, "Invalid url. Please try again.", Toast.LENGTH_LONG).show();
				}
				
			}
        	
        	
        });
    }

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
