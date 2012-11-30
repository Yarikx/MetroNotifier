package com.cogniance.yarik.metronotifier;

import java.util.ArrayList
import android.app.Activity
import android.content.ComponentName
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.content.Context
import com.cogniance.yarik.metronotifier.ScalaUtils._
import scala.collection.JavaConversions._
import android.net.wifi.ScanResult
import android.content.IntentFilter

class DebugActivity extends Activity with Scalactivity {

  lazy val app = getApplication().asInstanceOf[App]
  lazy val list = findView[ListView](R.id.list);
  lazy val wifiManager = getSystemService(Context.WIFI_SERVICE).asInstanceOf[WifiManager];
  lazy val receiver = new WifiScanReceiver

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_debug);
  }
  
  protected override def onResume() {
    super.onResume();
    app.debugActivity=this;
    val filter = new IntentFilter
    filter.addAction("android.net.wifi.SCAN_RESULTS")
    registerReceiver(receiver, filter)
  }

  protected override def onPause() {
    super.onPause();
    app.debugActivity=null;
    unregisterReceiver(receiver);
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.activity_debug, menu);
    true
  }

  def updateList(names: Seq[ScanResult]) {
    val strings = names.map(x=> "name: %s, bssid: %s, signal=%s".format(x.SSID, x.BSSID, x.level))
    inUi {
      list.setAdapter(new ArrayAdapter(DebugActivity.this,
        android.R.layout.simple_list_item_1, strings));
    }
  }

  def scan(view: View) {
    wifiManager.startScan();
  }

}
