package com.cogniance.yarik.metronotifier

import android.content.ContentValues
import android.net.wifi.ScanResult

object Model {

  case class Spot(
    val id: Int,
    val ssid: String,
    val bssid: String,
    val station: Station)

  case class Station(
    val id: Int,
    val name: String)

  implicit def spot2contentValues(spot: Spot) = {
    import com.cogniance.yarik.metronotifier.db.DbOpenHelper._
    import java.lang.Integer
    
    val values = new ContentValues()
    values.put(ID, spot.id.asInstanceOf[Integer])
    values.put(SSID, spot.ssid)
    values.put(BSSID, spot.bssid)
    values.put(STATION_ID, spot.station.id.asInstanceOf[Integer])
    values
  }
  
  implicit def station2contentValues(station: Station) = {
    import com.cogniance.yarik.metronotifier.db.DbOpenHelper._
    import java.lang.Integer
    
    val values = new ContentValues()
    values.put(ID, station.id.asInstanceOf[Integer])
    values.put(NAME, station.name)
    values
  }
  
}