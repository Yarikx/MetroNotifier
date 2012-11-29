package com.cogniance.yarik.metronotifier;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DebugActivity extends Activity {

	App app;
	ListView list;
	WifiManager wifiManager;
	private ComponentName receiver;
	private PackageManager pm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debug);
		app = (App) getApplication();
		wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		list = (ListView) findViewById(R.id.list);
		receiver = new ComponentName(this, WifiScanReceiver.class);
		pm = this.getPackageManager();
	}

	@Override
	protected void onResume() {
		super.onResume();
		app.debugActivityRef.set(this);
		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);
	}

	@Override
	protected void onPause() {
		super.onPause();
		app.debugActivityRef.set(null);
		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_debug, menu);
		return true;
	}

	public void updateList(final ArrayList<String> names) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				list.setAdapter(new ArrayAdapter<String>(DebugActivity.this,
						android.R.layout.simple_list_item_1, names));
			}
		});
	}

	public void scan(View view) {
		wifiManager.startScan();
	}

}
