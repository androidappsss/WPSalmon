package com.balitechy.wpsalmon;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.balitechy.wpsalmon.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Listing extends BaseListActivity {
	
	List<String[]> orders = new ArrayList<String[]>();
	MyListAdapter adapter;
	SalmonPref setting;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing);
        
        setting = new SalmonPref(this);

        adapter = new MyListAdapter(this);
        setListAdapter(adapter);
        new LoadList("reload").execute();
        
        // Reload Button
        ImageView reloadBtn = (ImageView) findViewById(R.id.reload);
        reloadBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(setting.isReady()){
					new LoadList("reload").execute();
				}else{
					Toast.makeText(Listing.this, "Please fill the WPSalmon configuration first. Go to Menu -> Settings.", Toast.LENGTH_LONG).show();
				}
			}
		});
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		//Get the order detail to use in next activity
		String order_id = ((TextView) v.findViewById(R.id.order_id)).getText().toString();
		String order_total = ((TextView) v.findViewById(R.id.order_total)).getText().toString();
		String order_status = ((TextView) v.findViewById(R.id.order_status)).getText().toString();
		String order_date = ((TextView) v.findViewById(R.id.order_date)).getText().toString();
		String order_currency = ((TextView) v.findViewById(R.id.order_currency)).getText().toString();
		
		String[] order_data = new String[]{order_id, order_total, order_status, order_date, order_currency};
		
		Intent intent = new Intent(this, OrderAction.class);
		intent.putExtra("order_data", order_data);
		startActivity(intent);
	}



	public class LoadList extends AsyncTask<Void, Void, Void>{

    	private ProgressDialog progress;
    	private String jString = null;
    	private String mode;
    	
    	public LoadList(String m){
    		mode = m;
    	}
    	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress = new ProgressDialog(Listing.this);
			progress.setMessage("Loading recent orders...");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			URL apiurl = null;
			try {
				apiurl = new URL(setting.getOrderUrl());
				HttpConnection conn = new HttpConnection(Listing.this, apiurl);
				jString = conn.UrlGet();
			} catch (MalformedURLException e) {
				Toast.makeText(Listing.this, "Invalid URL format.", Toast.LENGTH_LONG).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			
			if(jString != null){
				//Parsing Json response
				try{
					JSONObject jObj = new JSONObject(jString); 
					
					JSONArray jArr = new JSONArray(jObj.getString("data"));
					
					if(mode == "reload"){
						orders.clear();
					}
					
					//Get Currency Code
					String currency = jObj.getString("currency");
					
					if(jArr.length() > 0){   
						for(int i=0; i<jArr.length(); i++){
							JSONObject jOrder = (JSONObject) jArr.get(i);
							String[] odata = new String[]{
															jOrder.getString("order_id"),
															jOrder.getString("order_status"),
															jOrder.getString("order_date"),
															jOrder.getString("order_total"),
															currency
														};
							orders.add(odata);
						}
						adapter.notifyDataSetChanged();
					}else{
						Toast.makeText(Listing.this, "No Orders to display.", Toast.LENGTH_LONG).show();
						finish();
					}
				}catch(Exception e){
					Toast.makeText(Listing.this, "Failed fetching order data. Make sure WPSalmon configuration is correct.", Toast.LENGTH_LONG).show();
					finish();
				}
			}else{
				Toast.makeText(Listing.this, "Failed Connecting!. Please check your configuration and make sure you have internet connection available.", Toast.LENGTH_LONG).show();
				finish();
			}
		}
    }
    
    public class MyListAdapter extends BaseAdapter{
    	
    	private Context context;

    	public MyListAdapter(Context c){
    		context = c;
    	}
    	
		@Override
		public int getCount() {
			return orders.size();
		}

		@Override
		public Object getItem(int position) {
			return orders.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.list_item, parent, false);
			}
			
			TextView uname = (TextView) convertView.findViewById(R.id.order_id);
			uname.setText(orders.get(position)[0]);
			
			TextView oid = (TextView) convertView.findViewById(R.id.order_status);
			oid.setText(orders.get(position)[1]);
			
			TextView odate = (TextView) convertView.findViewById(R.id.order_date);
			odate.setText(orders.get(position)[2]);
			
			TextView ototal = (TextView) convertView.findViewById(R.id.order_total);
			ototal.setText(orders.get(position)[3]);
			
			TextView ocurr = (TextView) convertView.findViewById(R.id.order_currency);
			ocurr.setText(orders.get(position)[4]);
			
			return convertView;
		}
    	
    }
}
