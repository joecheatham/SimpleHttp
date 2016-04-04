package com.joecheatham.simplehttp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Joe on 4/4/16.
 */
class HttpGetTask extends AsyncTask<String,Void,String> {
  protected SimpleHttpResponseHandler delegate;
  protected int responseCode;

  public HttpGetTask(SimpleHttpResponseHandler delegate) {
    this.delegate = delegate;
  }

  protected String doInBackground(String... urls) {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      URL url = new URL(urls[0]);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

      responseCode = urlConnection.getResponseCode();

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
    Log.d(SimpleHttp.TAG, "response: " + responseCode + " received");
    delegate.onResponse(responseCode, result);
  }
}
