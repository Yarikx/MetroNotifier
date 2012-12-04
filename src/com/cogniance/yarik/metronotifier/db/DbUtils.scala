package com.cogniance.yarik.metronotifier.db

import android.database.sqlite.SQLiteDatabase
import android.content.Context
import com.cogniance.yarik.metronotifier.Model._
import com.cogniance.yarik.metronotifier.db.DbOpenHelper._
import android.content.ContentValues
import android.database.Cursor

object DbUtils {
  
  implicit def cursor2rich(c:Cursor)=new RichCursor(c)
  
  class RichCursor(u:Cursor) extends Iterable[Cursor]{
    override def iterator = new Iterator[Cursor]{
      def hasNext = !u.isLast()
      def next = {
        u.moveToNext()
        u
      }
    }
  }

  def inDb[A](f: SQLiteDatabase => A)(implicit context: Context):A={
    val db = new DbOpenHelper(context).getWritableDatabase();
    try {
      return f(db)
    } finally {
      db.close()
    }

  }

  def saveSpots(spots: Seq[Spot])(implicit context: Context) = {
    inDb {
      db =>
        spots.foreach(spot =>
          db.insert(TABLE_SPOTS, null, spot))
    }
  }

  def saveStation(stationName: String)(implicit context: Context) = {
    inDb {
      db =>
        val values = new ContentValues
        values.put(NAME, stationName)
        val resId = db.insert(TABLE_STATIONS, null, values)
        require(resId != -1)
        Station(resId, stationName)
    }
  }
  //select sp._id, sp.bssid, sp.ssid, st.name, st._id from spots sp inner join stations st on sp.station_id=st._id;
  def getAllSpots(implicit context: Context)={
    inDb{ db =>
      
      val stations = getAllStations
      val map = stations.map(s => s.id -> s).toMap
      val cursor = db.query(TABLE_SPOTS, null, null, null, null, null, null, null);
          
      val spots = cursor.map{c=>
        val id = c.getLong(c.getColumnIndex("_id"))
        val ssid = c.getString(c.getColumnIndex("ssid"))
        val bssid = c.getString(c.getColumnIndex("ssid"))
        val station = map.get(c.getLong(c.getColumnIndex(STATION_ID))).get
        
        Spot(id, ssid, bssid, station)
      }
      cursor.close
      
      spots.toList
      
    }
  }
  
  //select * from stations;
  def getAllStations(implicit context: Context)={
    inDb{ db =>
      val cursor = db.query(TABLE_STATIONS, null, null, null, null, null, null, null);
      val stations = cursor.map{c =>
        Station(c.getLong(c.getColumnIndex(ID)), c.getString(c.getColumnIndex(NAME)));
      }
      cursor.close();
      stations.toList
    }
  }

}