package com.joecheatham.simplehttp;

/**
 * Created by Joe on 4/4/16.
 */
public interface SimpleHttpResponseHandler {
  public void onResponse(int responseCode, String responseBody);
}
