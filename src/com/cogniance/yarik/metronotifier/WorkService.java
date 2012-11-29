package com.cogniance.yarik.metronotifier;

import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

public class WorkService extends IntentService{

	WifiManager manager;
	App app;
	
	public WorkService() {
		super("Wifi notification service");
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		app = (App) getApplication();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		List<ScanResult> results = manager.getScanResults();
		ArrayList<String> names = new ArrayList<String>(results.size());
		for(ScanResult result: results){
			names.add(result.SSID);
		}
		app.setScanResults(names);
	}

}
