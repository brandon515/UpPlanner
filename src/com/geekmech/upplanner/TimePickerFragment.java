/**
 * 
 */
package com.geekmech.upplanner;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.DialogFragment;
import android.text.format.DateFormat;

/**
 * @author Brandon
 *
 */
public class TimePickerFragment extends DialogFragment {
	
	TimePickerDialog.OnTimeSetListener mListener;
	int hour, minute;
	
	public TimePickerFragment(Calendar c){
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), mListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (TimePickerDialog.OnTimeSetListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement TimePickerDialog.OnTimeSetListener");
        }
    }
}
