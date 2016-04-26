package org.cpblog.app;

 

import org.kymjs.kjframe.bitmap.BitmapConfig;
import org.kymjs.kjframe.http.HttpConfig;

import android.app.Application;

public class AppContext extends Application{
 
	 public static int screenW;
	 public static int screenH;
	 
	 @Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	    BitmapConfig.CACHEPATH = AppConfig.imgCachePath;
	    HttpConfig.CACHEPATH = AppConfig.httpCachePath;
	 }
	
}
