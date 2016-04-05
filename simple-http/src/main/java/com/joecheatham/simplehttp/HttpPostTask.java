package com.joecheatham.simplehttp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Handles HTTP POST operations asynchronously.
 * @author <a href="https://github.com/joecheatham">joecheatham</a>
 * @version 1.0
 */
class HttpPostTask extends AsyncTask<String,Void,String> {
  protected SimpleHttpResponseHandler delegate;
  protected Map<String, String> data;
  protected int responseCode;

  public HttpPostTask(Map<String, String> data, SimpleHttpResponseHandler delegate) {
    this.delegate = delegate;
    this.data = data;
  }

  protected String doInBackground(String... urls) {
    StringBuilder response = new StringBuilder();
    try {
      URL url = new URL(urls[0]);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

      urlConnection.setReadTimeout(15000);
      urlConnection.setConnectTimeout(15000);
      urlConnection.setRequestMethod("POST");
      urlConnection.setDoInput(true);
      urlConnection.setDoOutput(true);

      OutputStream os = urlConnection.getOutputStream();
      BufferedWriter writer = new BufferedWriter(
          new OutputStreamWriter(os, "UTF-8"));
      writer.write(getPostDataString(data));

      writer.flush();
      writer.close();
      os.close();

      responseCode = urlConnection.getResponseCode();

      if (responseCode == HttpsURLConnection.HTTP_OK) {
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream
            ()));
        while ((line=br.readLine()) != null) {
          response.append(line);
        }
      }
      else {
        response.append("");

      }
    } catch (MalformedURLException e) {
      Log.e(SimpleHttp.TAG, e.getMessage());
    } catch (UnsupportedEncodingException e) {
      Log.e(SimpleHttp.TAG, e.getMessage());
    } catch (ProtocolException e) {
      Log.e(SimpleHttp.TAG, e.getMessage());
    } catch (IOException e) {
      Log.e(SimpleHttp.TAG, e.getMessage());
    }

    return response.toString();
  }

  protected void onPostExecute(String result) {
    Log.d(SimpleHttp.TAG, "POST response: " + responseCode + " received");
    delegate.onResponse(responseCode, result);
  }

  private String getPostDataString(Map<String, String> params) throws UnsupportedEncodingException {
    StringBuilder result = new StringBuilder();
    boolean first = true;
    for(Map.Entry<String, String> entry : params.entrySet()) {
      if (first) {
        first = false;
      } else {
        result.append("&");
      }

      result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
      result.append("=");
      result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
    }

    return result.toString();
  }
}
