package com.balitechy.wpsalmon;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class OrderDetail extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_detail, menu);
        return true;
    }
}
