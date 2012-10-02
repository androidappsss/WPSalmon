package com.balitechy.wpsalmon;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.util.Log;

public class HttpConnection {
	
	private URL httpurl;
	private Context context;
	
	public HttpConnection(Context c, URL url){
		httpurl = url;
		context = c;
	}

	public String UrlGet() {
        
        HttpURLConnection conn = null;
        String result = null;
		try {
			conn = (HttpURLConnection) httpurl.openConnection();
	        try{
	        	InputStream in = new BufferedInputStream(conn.getInputStream());;
	        	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        	StringBuilder sb = new StringBuilder();
	        	String line;
	        	while((line = br.readLine()) != null){
	        		sb.append(line);
	        	}
	        	in.close();
	        	result = sb.toString();
	        } catch (Exception e) {
				e.printStackTrace();
			}finally{
	        	conn.disconnect();
	        }
		} catch (Exception e) {
			Log.i("ERROR", "Connection", e);
		}
        return result;

	}

}