package com.geekmech.upplanner;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class NewEvent extends Activity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
	
	private Button timeButton;
	private Button dateButton;
	@SuppressWarnings("unused")
	private String name, setting;
	private Calendar date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		timeButton = (Button)findViewById(R.id.TimeButton);
		dateButton = (Button)findViewById(R.id.DateButton);
		final Calendar cal = Calendar.getInstance();
		date = cal;
		final String date = cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR);
		dateButton.setText(date);
		final int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
		String amPm, hour;
		if(hourOfDay > 12)
		{
			hour = Integer.toString(hourOfDay-12);
			amPm = "PM";
		}
		else if(hourOfDay == 0)
		{
			hour = "12";
			amPm = "AM";
		}
		else
		{
			hour = Integer.toString(hourOfDay);
			amPm = "AM";
		}
		String mMinute;
		int minute = cal.get(Calendar.MINUTE);
		if(minute > 10){
			mMinute = Integer.toString(minute);
		}else{
			mMinute = "0" + Integer.toString(minute);
		}
		final String time = hour + ":" + mMinute + " " + amPm;
		timeButton.setText(time);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == R.id.action_send)
		{
			EditText nameView = (EditText)findViewById(R.id.EventName);
			EditText settingView = (EditText)findViewById(R.id.EventSetting);
			name = nameView.getText().toString();
			setting = settingView.getText().toString();
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void dateClick(View v){
		DialogFragment newFrag = new DatePickerFragment(date);
		newFrag.onAttach(this);
		newFrag.show(getFragmentManager(), "Date of Event");
	}
	
	public void timeClick(View v){
		DialogFragment newFrag = new TimePickerFragment(date);
		newFrag.onAttach(this);
		newFrag.show(getFragmentManager(), "Time of Event");
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		date.set(Calendar.HOUR_OF_DAY, hourOfDay);
		date.set(Calendar.MINUTE, minute);
		final String mMinute;
		String hour, amPm;
		if(minute > 10){
			mMinute = Integer.toString(minute);
		}else{
			mMinute = "0" + Integer.toString(minute);
		}
		if(hourOfDay > 12)
		{
			hour = Integer.toString(hourOfDay-12);
			amPm = "PM";
		}
		else if(hourOfDay == 0)
		{
			hour = "12";
			amPm = "AM";
		}
		else
		{
			hour = Integer.toString(hourOfDay);
			amPm = "AM";
		}
		final String time = hour + ":" + mMinute + " " + amPm;
		timeButton.setText(time);
		
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		date.set(Calendar.MONTH, monthOfYear);
		date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		date.set(Calendar.YEAR, year);
		dateButton.setText(monthOfYear+1 + "/" + dayOfMonth + "/" + year);
	}
}
