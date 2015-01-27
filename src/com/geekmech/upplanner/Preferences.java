/**
 * 
 */
package com.geekmech.upplanner;

import java.io.File;
import java.io.InputStream;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

/**
 * @author Brandon
 *
 */
public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	public static final String TAG = "Preferences";
	private Prefs1 prefFrag;
	
	private Drawable getImageFromPreference(SharedPreferences pref, String key){
		if(pref == null){
			pref = PreferenceManager.getDefaultSharedPreferences(this);
		}
		String uri_string = pref.getString(key, "");
		if(uri_string.equals("")){
			return null;
		}
		Drawable image = null;
		try{
			Uri uri = Uri.parse(uri_string);
			InputStream inst = getContentResolver().openInputStream(uri);
			image = Drawable.createFromStream(inst, "tester");
			
		}catch(Exception e){
			Log.e(TAG, e.getLocalizedMessage());
			return null;
		}
		return image;
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		prefFrag = new Prefs1();
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		pref.registerOnSharedPreferenceChangeListener(this);
		
		getFragmentManager().beginTransaction()
			.replace(android.R.id.content, prefFrag)
			.commit();
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    PreferenceManager.getDefaultSharedPreferences(this)
	            .registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    PreferenceManager.getDefaultSharedPreferences(this)
	            .unregisterOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	protected boolean isValidFragment(String fragmentName){
		return Prefs1.class.getName().equals(fragmentName);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode == RESULT_OK){
			if(requestCode == Crop.REQUEST_PICK){
				Uri uri = data.getData();
				Crop crop = new Crop(uri);
				crop.asSquare().withMaxSize(2000, 2000);
				ContextWrapper cw = new ContextWrapper(this);
				File dir = cw.getDir("avatars", Context.MODE_PRIVATE);
				File im = new File(dir, "avatar.png");
				Uri outputUri = Uri.fromFile(im);
				crop.output(outputUri);
				crop.start(this);
			}
			else if(requestCode == Crop.REQUEST_CROP){
				Uri uri = data.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
				SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
				Editor edit = pref.edit();
				edit.putString(getString(R.string.avatar_pref_key), uri.toString());
				edit.apply();
			}
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if(key == getString(R.string.avatar_pref_key)){
			Drawable image = getImageFromPreference(sharedPreferences ,getString(R.string.avatar_pref_key));
			if(image == null){
				Toast.makeText(this, "Something went wrong with retrieving your avatar from SharedPreference", Toast.LENGTH_LONG).show();
			}
			Preference avatar = (Preference) prefFrag.findPreference(getString(R.string.avatar_pref_key));
			avatar.setIcon(image);
		}
		
	}
	
	
	
	public static class Prefs1 extends PreferenceFragment{
		public static final String TAG = "Prefs1";
		
		private Drawable getImageFromPreference(SharedPreferences pref, String key){
			if(pref == null){
				pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
			}
			String uri_string = pref.getString(key, "");
			if(uri_string.equals("")){
				return null;
			}
			Drawable image = null;
			try{
				Uri uri = Uri.parse(uri_string);
				InputStream inst = getActivity().getContentResolver().openInputStream(uri);
				image = Drawable.createFromStream(inst, "tester");
				
			}catch(Exception e){
				Log.e(TAG, e.getLocalizedMessage());
				return null;
			}
			return image;
			
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			
			addPreferencesFromResource(R.xml.general_pref);
			
			Drawable image = getImageFromPreference(null, getString(R.string.avatar_pref_key));
			if(image == null){
				Toast.makeText(getActivity(), "Something went wrong with retrieving your avatar from SharedPreference", Toast.LENGTH_LONG).show();
			}
			
			final Preference avatar = (Preference) findPreference(getString(R.string.avatar_pref_key));
			avatar.setIcon(image);
			
			OnPreferenceClickListener avatarClick = new Preference.OnPreferenceClickListener() {
				
				@Override
				public boolean onPreferenceClick(Preference preference) {
					if(preference == avatar){
						Crop.pickImage(getActivity());
					}
					return true;
				}
			};
			
			avatar.setOnPreferenceClickListener(avatarClick);
		}
	}
}
