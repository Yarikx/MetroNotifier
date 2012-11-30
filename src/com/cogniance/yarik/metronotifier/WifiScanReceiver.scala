package com.cogniance.yarik.metronotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

class WifiScanReceiver extends BroadcastReceiver{

	override def onReceive(context: Context,intent: Intent) {
		Log.d("wifi", "networks received");
		context.startService(new Intent(context, classOf[WorkService]));
	}

}
