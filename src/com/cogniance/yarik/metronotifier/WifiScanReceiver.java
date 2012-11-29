package com.cogniance.yarik.metronotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WifiScanReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("wifi", "networks received");
		context.startService(new Intent(context, WorkService.class));
	}

}
