package com.joecheatham.simplehttp;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Handles HTTP GET operations asynchronously.
 * @author <a href="https://github.com/joecheatham">joecheatham</a>
 * @version 1.0
 */
class HttpGetTask extends AsyncTask<String,Void,String> {
  protected SimpleHttpResponseHandler mDelegate;
  protected Map<String, String> mHeaders;
  protected int mResponseCode;

  public HttpGetTask(SimpleHttpResponseHandler delegate, Map<String, String> headers) {
    mDelegate = delegate;
    mHeaders = headers;
  }

  protected String doInBackground(String... urls) {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      URL url = new URL(urls[0]);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

      if (mHeaders != null) {
        for (Map.Entry<String, String> header : mHeaders.entrySet()) {
          urlConnection.setRequestProperty(header.getKey(), header.getValue());
        }
      }

      mResponseCode = urlConnection.getResponseCode();

      InputStream in = new BufferedInputStream(urlConnection.getInputStream());
      BufferedReader r = new BufferedReader(new InputStreamReader(in));
      String line;
      while ((line = r.readLine()) != null) {
        stringBuilder.append(line);
      }
      urlConnection.disconnect();

    } catch (MalformedURLException e) {
      Log.e(SimpleHttp.TAG, e.getMessage());
    } catch (IOException e) {
      Log.e(SimpleHttp.TAG, e.getMessage());
      ;
    }
    return stringBuilder.toString();
  }

  protected void onPostExecute(String result) {
    Log.d(SimpleHttp.TAG, "response: " + mResponseCode + " received");
    mDelegate.onResponse(mResponseCode, result);
  }
}
