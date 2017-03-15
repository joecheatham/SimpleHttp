package com.joecheatham.simplehttp;

import android.util.Log;

import java.util.Map;

/**
 * Handles HTTP operations asynchronously.
 * @author <a href="https://github.com/joecheatham">joecheatham</a>
 * @version 1.0
 */
public class SimpleHttp {
  public static final String TAG = SimpleHttp.class.getCanonicalName();

  public static void get(String url, SimpleHttpResponseHandler callback) {
    get(url, null, callback);
  }

  public static void get(
      String url,
      Map<String, String> headers,
      SimpleHttpResponseHandler callback) {
    Log.d(TAG, "GET called to: " + url);
    new HttpGetTask(callback, headers).execute(url);
  }

  public static void post(
      String url,
      Map<String, String> parameters,
      SimpleHttpResponseHandler callback) {
    post(url, parameters, null, callback);
  }

  public static void post(
      String url,
      Map<String, String> parameters,
      Map<String, String> headers,
      SimpleHttpResponseHandler callback) {
    Log.d(TAG, "POST called to: " + url);
    new HttpPostTask(parameters, headers, callback).execute(url);
  }
}
