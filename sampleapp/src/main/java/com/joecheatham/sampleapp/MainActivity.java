package com.joecheatham.sampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joecheatham.simplehttp.SimpleHttp;
import com.joecheatham.simplehttp.SimpleHttpResponseHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
  public static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button postButton = (Button) findViewById(R.id.buttonPost);
    Button getButton = (Button) findViewById(R.id.buttonGet);

    postButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Map<String, String> params = new HashMap<>();
        params.put("test", "simple-http rocks!");
        SimpleHttp.post("http://httpbin.org/post", params, new SimpleHttpResponseHandler() {
          @Override
          public void onResponse(int responseCode, String responseBody) {
            try {
              JSONObject json = new JSONObject(responseBody);
              String value = json.getJSONObject("form").getString("test");
              Toast.makeText(getApplicationContext(), "POST received! param test value: " + value, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
              Log.e(TAG, e.getMessage());
            }
            Log.i(TAG, responseBody);
          }
        });
      }
    });

    getButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SimpleHttp.get("http://httpbin.org/get", new
            SimpleHttpResponseHandler() {
              @Override
              public void onResponse(int responseCode, String responseBody) {
                Toast.makeText(getApplicationContext(), "GET received!", Toast.LENGTH_SHORT).show();
                Log.i(TAG, responseBody);
              }
            });
      }
    });
  }
}
