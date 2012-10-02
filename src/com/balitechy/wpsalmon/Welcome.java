/*
 * Project started: September 20, 2012
 */

package com.balitechy.wpsalmon;

import com.balitechy.wpsalmon.R;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Welcome extends BaseActivity {
	
	SalmonPref setting;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        
        setting = new SalmonPref(this);
        
        ImageView reloadBtn = (ImageView) findViewById(R.id.reload);
        reloadBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startWpSalmon();
			}
		});
        
        ImageView startBtn = (ImageView) findViewById(R.id.start);
        startBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startWpSalmon();
			}
		});

    }
    
    public void startWpSalmon(){
    	if(setting.isReady()){
			Intent listIntent = new Intent(Welcome.this, Listing.class);
			startActivity(listIntent);
		}else{
			Toast.makeText(Welcome.this, "Please fill the WPSalmon configuration first. Go to Menu -> Settings.", Toast.LENGTH_LONG).show();
		}
    }
}
