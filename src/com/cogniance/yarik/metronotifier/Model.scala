package com.cogniance.yarik.metronotifier

import android.content.ContentValues
import android.net.wifi.ScanResult
import java.lang.Long

object Model {
  
  trait DbEntity{
    val id:java.lang.Long
  }

  case class Spot(
    val ssid: String,
    val bssid: String,
    val station: Station)
    
  object Spot{
    def apply(rId: Long, ssid:String, bssid: String, station: Station)={
      new Spot(ssid, bssid, station) with DbEntity{val id = rId}
    }
  }

  case class Station(
    val name: String)
  
  object Station{
    def apply(rId: Long, name:String)=
      new Station(name) with DbEntity{val id = rId}
  }

  implicit def spot2contentValues(spot: Spot) = {
    import com.cogniance.yarik.metronotifier.db.DbOpenHelper._
    import java.lang.Long
    
    val values = new ContentValues()
    values.put(SSID, spot.ssid)
    values.put(BSSID, spot.bssid)
    spot.station match{
      case e: DbEntity => values.put(STATION_ID, e.id)
      case _ => throw new IllegalStateException("station has no id")
    }
    values
  }
  
  implicit def station2contentValues(station: Station) = {
    import com.cogniance.yarik.metronotifier.db.DbOpenHelper._
    import java.lang.Integer
    
    val values = new ContentValues()
    values.put(NAME, station.name)
    values
  }
  
}