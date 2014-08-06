/* Ariana Antonio
 * August 5, 2014
 * Full Sail University, Mobile Development
 * MDF3
 * Purpose: To provide an app that will call the Octopus browser app
 */ 
package com.arianaantonio.stubapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	EditText url;
	Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = (EditText) findViewById(R.id.editText1);
        final String enteredURL = (String) url.getText().toString();
        
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("Main Activity", "Entered URL: " +enteredURL);
				
			}
        	
        	
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}