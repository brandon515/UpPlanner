package com.geekmech.upplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {
	public final static String EVENT_NAME = "com.geekmech.upplanner.EVENT_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.general_pref, false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add_person){
        	Intent intent = new Intent(this, FriendActivity.class);
        	startActivity(intent);
        	return true;
        }
        else if(id == R.id.add_event){
        	Intent intent = new Intent(this, NewEvent.class);
        	startActivity(intent);
        	return true;
        }
        else if(id == R.id.action_settings){
        	//DataContract data = new DataContract();
        	//data.createProfileResponse(this);
        	Intent intent = new Intent(this, Preferences.class);
        	startActivity(intent);
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
