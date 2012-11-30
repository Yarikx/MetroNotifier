package com.cogniance.yarik.metronotifier.db

import android.database.sqlite.SQLiteDatabase
import android.content.Context
import com.cogniance.yarik.metronotifier.Model._
import com.cogniance.yarik.metronotifier.db.DbOpenHelper._;
import android.content.ContentValues

object DbUtils {

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
        val id = db.insert(TABLE_STATIONS, null, values)
        require(id != -1)
        Station(id, stationName)
    }
  }

}