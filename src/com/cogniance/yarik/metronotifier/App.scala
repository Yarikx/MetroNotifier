package com.cogniance.yarik.metronotifier;

import java.util.ArrayList
import java.util.concurrent.atomic.AtomicReference
import android.app.Application;
import android.net.wifi.ScanResult

class App extends Application{
	
	private val debugActivityRef = new AtomicReference[DebugActivity]();
	
	def debugActivity=debugActivityRef.get()
	def debugActivity_= (value:DebugActivity):Unit = debugActivityRef.set(value)
	
	override def onCreate() {
		super.onCreate();
	}

	def setScanResults(names: Seq[ScanResult]) {
		val debugActivity = debugActivityRef.get();
		if(debugActivity != null){
			debugActivity.updateList(names);
		}
	}
	
	

}
