package com.geekmech.upplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class DataDealer extends BroadcastReceiver {
	
	private static final String TAG = "DataDealer";
	
	private void handleData(byte[] data, String fromAddress){
		byte id = data[0];
		if(id == DataContract.DATA_ID_PING){
			Log.i(TAG, "Ping received from " + fromAddress + ", responding");
			
			
		}
		else if(id == DataContract.DATA_ID_EVENT){
			Log.i(TAG, "Event recieved from " + fromAddress);
			
		}
		else if(id == DataContract.DATA_ID_FRIEND){
			Log.i(TAG, "Friend request recieved from " + fromAddress);
			
		}
		else if(id == DataContract.DATA_ID_PING_RESPONSE){
			Log.i(TAG, "Ping response from " + fromAddress);
			Log.i(TAG, Integer.toString(data.length));
			
		}
			
	}
	
	public static void sendData(String dest, byte[] data, Context context){
		final String fDest = dest;
		final byte[] fData = data;
		final Context fContext = context;
		new Thread(new Runnable(){
			public void run(){
				SmsManager manager = SmsManager.getDefault();
				manager.sendDataMessage(fDest, "+19092796499", (short)fContext.getResources().getInteger(R.integer.sms_port), fData, null, null);
			}
		}).start();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "got here");
		Bundle bundle = intent.getExtras();
		SmsMessage recMsg = null;
		byte[] data = null;
		if(bundle != null){
			Object[] pdus = (Object[])bundle.get("pdus");
			for(int i = 0; i < pdus.length; i++){
				recMsg = SmsMessage.createFromPdu((byte[])pdus[i]);
				try{
					data = recMsg.getUserData();
				}catch(Exception e){
					
				}
				if(data != null){
					handleData(data, recMsg.getOriginatingAddress());
				}
			}
		}

	}

}
