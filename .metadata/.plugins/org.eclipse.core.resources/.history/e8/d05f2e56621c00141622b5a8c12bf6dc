package com.arianaantonio.octopus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
	break;
	case R.id.backward:
	Log.i("Main Activity", "Selected 'Backward'");
	break;
	case R.id.forward:
	Log.i("Main Activity", "Selected 'Forward'");
	break;
	}
	return true;
	} 
    
}
