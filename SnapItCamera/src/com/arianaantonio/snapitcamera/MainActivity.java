/*
 * Author: Ariana Antonio
 * 
 * Project: SnapItCamera App
 * 
 * Package: com.arianaantonio.snapitcamera
 * 
 * File: MainActivity.java
 * 
 * Purpose: An app used to take photos and store them in the gallery of the device and to be view within the app  
 * 
 */

package com.arianaantonio.snapitcamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

public class MainActivity extends Activity {

	Button button;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static Uri fileUri;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	Camera mCamera;
	private SmartImageView imageView;
	Context context;
	static File mediaFile;
	


	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				fileUri = getOutputMediaFileUri(1); 
				Log.i("Main Activity", "File Uri: " +fileUri);
		        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
        	
        });
        	
        
    }
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }
    @SuppressLint("SimpleDateFormat")
	private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
    	
    	BroadcastReceiver mExternalStorageReceiver;  
    	boolean mExternalStorageAvailable = false;
    	boolean mExternalStorageWriteable = false;

    	
    	String storageState = Environment.getExternalStorageState();
    	if (Environment.MEDIA_MOUNTED.equals(storageState)) {
    		Log.i("Main Activity", "Storage Available");
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(storageState)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        //handleExternalStorageState(mExternalStorageAvailable,
                //mExternalStorageWriteable);
    
 
    	

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "SnapItCamera");


        Log.i("Main Activity", "Image Storage: " +mediaStorageDir);
      
        if (! mediaStorageDir.exists()){
        	Log.i("Main Activity", "Storage does not exist");
            if (! mediaStorageDir.mkdirs()){
                Log.d("SnapItCamera", "failed to create directory");
                return null;
            }
        }

       
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        Log.i("Main Activity", "File Name: " +mediaFile);
        return mediaFile;
    }
 
    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();      
            mCamera = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
           
            	Log.i("Main Activity", "Activity Result File Uri: " +fileUri);
            	imageView = (SmartImageView) findViewById(R.id.my_image);
            	imageView.setImageURI(fileUri);
      
            	Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            	intent.setData(fileUri);
            	sendBroadcast(intent); 
            	Bitmap bitmap = null;
				try {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	NotificationCompat.Builder mBuilder =
            		    new NotificationCompat.Builder(this)
            		    .setSmallIcon(R.drawable.ic_launcher)
            		    .setContentTitle("Image Saved")
            		    .setLargeIcon(bitmap)
            		    .setContentText("Your image was saved to the Gallery");
            	int mNotificationId = 001;
            	NotificationManager mNotifyMgr = 
            	        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            	String newfileUri = "content://media/external/images/media";  
            
            	Intent newIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newfileUri));
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            	mBuilder.setContentIntent(pendingIntent);
            	mNotifyMgr.notify(mNotificationId, mBuilder.build());
            } else if (resultCode == RESULT_CANCELED) {
            	Toast.makeText(this, "Save canceled", Toast.LENGTH_LONG).show();
            } else { 
            	Toast.makeText(this, "Save failed", Toast.LENGTH_LONG).show();
            }
        }
        releaseCamera();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fileUri != null) {
            outState.putString("cameraImageUri", fileUri.toString());
            outState.putString("cameraImageFilePath", mediaFile.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("cameraImageUri")) {
            fileUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
            mediaFile = new File(savedInstanceState.getString("cameraImageFilePath"));
            Log.i("Main Activity", "File Uri from intent: " +fileUri);
        }
    } 
}
