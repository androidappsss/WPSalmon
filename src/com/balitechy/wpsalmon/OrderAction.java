package com.balitechy.wpsalmon;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OrderAction extends BaseListActivity {
	
	public List<String[]> actions = new ArrayList<String[]>();
	public ActionListAdapter actionLD;
	public String[] order_data;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_action);
        
        //Get passed data from previous activity
        Intent intent = getIntent();
        order_data = intent.getStringArrayExtra("order_data");
        setTitle("Order #"+order_data[0]);
        
        //Disable the reload button
        ImageView reload = (ImageView) findViewById(R.id.reload);
        reload.setVisibility(View.GONE);
        
        //Build our action list content
        actions.add(new String[]{"1", "Order detail", "View detail of this order."});
        actions.add(new String[]{"2", "Customer info", "Get customer information."});
        actions.add(new String[]{"3", "Send email", "Send customer an email."});
        actions.add(new String[]{"4", "Call", "If they provide phone/mobile number."});
        actions.add(new String[]{"5", "Send text message", "If they provide phone/mobile number."});
        
        actionLD = new ActionListAdapter(this);
        setListAdapter(actionLD);
    }
    
    
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	
    	switch(position){
    		case 0:
    			Intent detailIntent = new Intent(this, OrderDetail.class);
    			startActivity(detailIntent);
    			break;
    		case 1:
    			break;
    	}
		
	}

	public class ActionListAdapter extends BaseAdapter{
    	private Context context;
    	
    	public ActionListAdapter(Context c){
    		context = c;
    	}

		@Override
		public int getCount() {
			return actions.size();
		}

		@Override
		public Object getItem(int position) {
			return actions.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.order_action_item, parent, false);
			}
			
			TextView action_id = (TextView) convertView.findViewById(R.id.action_id);
			action_id.setText(actions.get(position)[0]);
			
			TextView action_title = (TextView) convertView.findViewById(R.id.action_title);
			action_title.setText(actions.get(position)[1]);
			
			TextView action_desc = (TextView) convertView.findViewById(R.id.action_desc);
			action_desc.setText(actions.get(position)[2]);
			
			return convertView;
		}
    }
}
