package com.geekmech.upplanner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;


public class DataContract{
	
	private final static String TAG = "Data Contract";
	
	public final static byte DATA_ID_PING = (byte)0x01;
	public final static byte DATA_ID_EVENT = (byte)0X02;
	public final static byte DATA_ID_FRIEND = (byte)0X03;
	public final static byte DATA_ID_PROFILE_REQUEST = (byte)0X04;
	public final static byte DATA_ID_PROFILE_RESPONSE = (byte)0X05;
	public final static byte DATA_ID_PING_RESPONSE = (byte)0xff;
	public final static short SMS_PORT = 5000;
	
	public static byte[] createPingRequest(){
		byte[] data = new byte[]{DATA_ID_PING};
		return data;
	}
	
	public static byte[] createFriendRequest(){
		byte[] data = new byte[]{DATA_ID_FRIEND};
		/*ByteArrayOutputStream output = new ByteArrayOutputStream();
		try{
		output.write(data);
		}catch(IOException e)
		{
			Log.e(TAG, e.getLocalizedMessage());
			return null;
		}
		data = output.toByteArray();*/
		return data;
	}
	
	public static byte[] createProfileRequest(){
		byte[] data = new byte[]{DATA_ID_PROFILE_REQUEST};
		return data;
	}
	
	public byte[] createProfileResponse(Context context){
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		String name = pref.getString(context.getString(R.string.name_pref), "");
		if(name.equals("")){
			Log.e(TAG, "No name preference, this should never happen");
			return null;
		}
		String uri_string = pref.getString(context.getString(R.string.avatar_pref_key), "sad panda");
		Drawable image = null;
		try{
			Uri uri = Uri.parse(uri_string);
			InputStream inst = context.getContentResolver().openInputStream(uri);
			image = Drawable.createFromStream(inst, "tester");
			
		}catch(Exception e){
			Log.e(TAG, e.getLocalizedMessage());
		}
		Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] data = new byte[]{DATA_ID_PROFILE_RESPONSE};
		byte[] nameArray = name.getBytes();
		byte[] bitmapData = stream.toByteArray();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try{
		output.write(data);
		output.write(nameArray);
		output.write(bitmapData);
		}catch(IOException e)
		{
			Log.e(TAG, e.getLocalizedMessage());
			return null;
		}
		data = output.toByteArray();
		//DataDealer.sendData("+19092796499", data, context);
		return data;
	}
	
	//FOR TESTING, REMOVE
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

}
