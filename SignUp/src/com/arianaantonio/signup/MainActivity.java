/*
 * Author: Ariana Antonio
 * 
 * Project: SignUp
 * 
 * Package: com.arianaantonio.signup
 * 
 * File: MainActivity.java
 * 
 * Purpose: The activity is inflating the webview and receiving the input data from the Javascript code. Activity then launches
 * an email intent with the data.
 */ 

package com.arianaantonio.signup; 


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView; 


@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity { 
 
	WebView newsletterWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		newsletterWebView = (WebView) findViewById(R.id.webview);
		newsletterWebView.loadUrl("file:///android_asset/signup.html");
		
		WebSettings settings = newsletterWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		newsletterWebView.addJavascriptInterface(new SignUpInterface(this), "Android");
		
		
	}
	public class SignUpInterface {
		Context mContext;
		
		SignUpInterface(Context context) {
			mContext = context;
		}
		@JavascriptInterface
		public void saveData(String name, String email, String birthday, String comment) {
			Log.i ("Main Activity", "Saving data");
			
			String emailText = "Hello " +name+ ",\n Thank you for signing up for our newsletter. Check your inbox on " +birthday+
					" for a birthday surprise! Here is the comment you left us: " +comment+ ", Have a great day!\n Regards,\n" +
							"The Team";
			Log.i("Main activity", "Email text: " +emailText);
			Intent intent = new Intent(Intent.ACTION_SENDTO);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_EMAIL, email);
			intent.putExtra(Intent.EXTRA_SUBJECT, "Welcome");
			intent.putExtra(Intent.EXTRA_TEXT, emailText);
			
			startActivity(Intent.createChooser(intent, "Send email"));
			
		}
	}
}
