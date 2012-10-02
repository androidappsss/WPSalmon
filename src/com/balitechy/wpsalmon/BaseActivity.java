package com.balitechy.wpsalmon;

import com.balitechy.wpsalmon.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int selected = item.getItemId();
		switch(selected){
			case R.id.menu_settings:
				Intent settingIntent = new Intent(this, Settings.class);
				startActivity(settingIntent);
				return true;
				
			default:
				return super.onMenuItemSelected(featureId, item);
		}
		
	}
    
    
}
