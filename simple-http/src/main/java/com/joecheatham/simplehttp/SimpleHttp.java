package com.joecheatham.simplehttp;

import android.util.Log;

import java.util.Map;

/**
 * Created by Joe on 4/4/16.
 */
public class SimpleHttp {
  public static final String TAG = "SimpleHttp";
  public static void get(String url, SimpleHttpResponseHandler callback) {
    Log.d(TAG, "GET called to: " + url);
    new HttpGetTask(callback).execute(url);
  }

  public static void post(String url, Map<String, String> parameters, SimpleHttpResponseHandler
      callback) {
    Log.d(TAG, "POST called to: " + url);
    new HttpPostTask(parameters, callback).execute(url);
  }
}
