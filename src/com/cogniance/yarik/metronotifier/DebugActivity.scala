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

class DebugActivity extends Activity with Scalactivity {

  var app: App = null
  var list: ListView = null
  var wifiManager: WifiManager = null
  var receiver: ComponentName = null
  var pm: PackageManager = null

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_debug);
    app = getApplication().asInstanceOf[App];
    wifiManager = getSystemService(Context.WIFI_SERVICE).asInstanceOf[WifiManager];
    list = findView[ListView](R.id.list);
    receiver = new ComponentName(this, classOf[WifiScanReceiver]);
    pm = this.getPackageManager();
  }
  
  protected override def onResume() {
    super.onResume();
    app.debugActivity=this;
    pm.setComponentEnabledSetting(receiver,
      PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
      PackageManager.DONT_KILL_APP);
    
//    asyncTask{
//      wifiManager.getScanResults().toList.map(x=> "name: %s, bssid: %s".format(x.SSID, x.BSSID))
//    }{
//      result =>
//        updateList(result)
//    }
  }

  protected override def onPause() {
    super.onPause();
    app.debugActivity=null;
    pm.setComponentEnabledSetting(receiver,
      PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
      PackageManager.DONT_KILL_APP);
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.activity_debug, menu);
    true
  }

  def updateList(names: Seq[ScanResult]) {
    val strings = names.map(x=> "name: %s, bssid: %s".format(x.SSID, x.BSSID))
    inUi {
      list.setAdapter(new ArrayAdapter(DebugActivity.this,
        android.R.layout.simple_list_item_1, strings));
    }
  }

  def scan(view: View) {
    wifiManager.startScan();
  }

}
