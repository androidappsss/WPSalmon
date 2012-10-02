package com.balitechy.wpsalmon;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SalmonPref {
	
	private SharedPreferences pref;
	private Context context;
	private String API_HOST;// = "http://10.0.2.2/wordpress/341/?";
	private String APP_ID;// = "998574";

	
	public SalmonPref(Context c){
		context = c;
		pref = PreferenceManager.getDefaultSharedPreferences(context);
		reload();

	}
	
	private void reload(){
		API_HOST = pref.getString("WPSALMON_API_HOST", "");
		APP_ID = pref.getString("WPSALMON_APP_ID", "");
	}
	
	public Boolean isReady(){
		reload();
		if(API_HOST != "" && APP_ID != ""){
			return true;
		}
		return false;
	}
	
	public String getOrderUrl(){
		String orderUrl = API_HOST+"?wpsl=get_orders&token="+APP_ID;
		return orderUrl;
	}

}
