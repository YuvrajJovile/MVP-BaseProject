package com.baseproject.library;

import com.indieculture.BuildConfig;

public class Log {

	public static void d(String name, String value) {
		if (BuildConfig.SHOW_LOG) {
			android.util.Log.d(name, value);
		}

	}
	
	public static void i(String name, String value) {
		if (BuildConfig.SHOW_LOG) {
			android.util.Log.i(name, value);
		}
	}
	
	public static void e(String name, String value) {
		if (BuildConfig.SHOW_LOG) {
			android.util.Log.e(name, value);
		}
	}
	
	public static void w(String name, String value) {
		if (BuildConfig.SHOW_LOG) {
			android.util.Log.e(name, value);
		}
	}
}
