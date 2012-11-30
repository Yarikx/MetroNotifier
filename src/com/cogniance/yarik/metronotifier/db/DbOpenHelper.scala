package com.cogniance.yarik.metronotifier.db

import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase

object DbOpenHelper{
  val TABLE_SPOTS="spots"
  val TABLE_STATIONS="stations"
  val ID="_id"
  val BSSID="bssid"
  val SSID="ssid"
  val STATION_ID="station_id"
  val NAME="name"
}

class DbOpenHelper(context: Context) extends SQLiteOpenHelper(context, "metro", null, 1){
  import DbOpenHelper._;
  val createSpotsStatement = 
    "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT, %s TEXT, %s INTEGER);";
  val createStationsStatement = 
    "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT, %s TEXT, %s INTEGER);";
  
  def onCreate(db:SQLiteDatabase){
    db.execSQL(createSpotsStatement format(TABLE_SPOTS, ID, BSSID, SSID, STATION_ID))
    db.execSQL(createSpotsStatement format(TABLE_STATIONS, ID, NAME))
  }
  
  def onUpgrade(db:SQLiteDatabase, oldVersion:Int, currentVersion:Int){
    
  }
  
}
