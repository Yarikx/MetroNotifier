package com.cogniance.yarik.metronotifier;

import java.util.ArrayList;
import java.util.List;
import scala.collection.JavaConversions._;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

class WorkService extends IntentService("Wifi notification service"){
  
	lazy val manager:WifiManager = getSystemService(Context.WIFI_SERVICE).asInstanceOf[WifiManager];
	lazy val app = getApplication().asInstanceOf[App];
	
	override def onCreate() {
		super.onCreate();
	}

	override def onHandleIntent(intent: Intent) {
		val results = manager.getScanResults();
		app.setScanResults(results);
	}

}
