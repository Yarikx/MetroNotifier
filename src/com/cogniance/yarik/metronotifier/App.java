package com.cogniance.yarik.metronotifier;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import android.app.Application;

public class App extends Application{
	
	public AtomicReference<DebugActivity> debugActivityRef = new AtomicReference<DebugActivity>();
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	public void setScanResults(ArrayList<String> names) {
		DebugActivity debugActivity = debugActivityRef.get();
		if(debugActivity != null){
			debugActivity.updateList(names);
		}
	}
	
	

}
