package com.cogniance.yarik.metronotifier

import android.app.Activity
import android.os.AsyncTask

object ScalaUtils {
  
  trait Scalactivity extends Activity{
    
    implicit val context = this
    
    def findView[A](id: Int) = findViewById(id).asInstanceOf[A]
    
    def inUi(r: Runnable)=runOnUiThread(r)
    def asyncTask[A](f: => A)(post: A=>Unit)=new AsyncTask[Void, Void, A](){
      override def doInBackground(p: Void*)=f
      override def onPostExecute(a:A)=post(a)
    }.execute()
  }
  
  implicit def unit2runnable(f: =>Unit)=new Runnable(){
    override def run()=f
  }
}