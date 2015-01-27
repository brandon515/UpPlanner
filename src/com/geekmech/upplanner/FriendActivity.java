package com.geekmech.upplanner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FriendActivity extends Activity {
	private static final int CONTACT_RES = 1001;
	private static final String TAG = "FriendActivity";
	private static EditText number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		number = (EditText)findViewById(R.id.FriendNumber);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_done) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void friendPicker(View view){
		Intent intent = new Intent(Intent.ACTION_PICK, Phone.CONTENT_URI);
		startActivityForResult(intent, CONTACT_RES);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode == RESULT_OK) {
            if(requestCode == CONTACT_RES){
                Uri result = data.getData();
                Log.i(TAG, result.toString());
                Cursor c = getContentResolver().query(result,null,null,null,null);
                Log.i(TAG, "Cursor loaded up");
            	if(c.moveToFirst()){
            		String phone = c.getString(c.getColumnIndex(Phone.NUMBER));
            		Log.i(TAG, "Phone number retrieved: " + phone);
            		number.setText(phone, TextView.BufferType.EDITABLE);
            	}
            }
        }
	}
}
