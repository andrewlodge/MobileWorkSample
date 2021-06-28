package com.example.lodge.a497final;


import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;



public class AddProcedureActivity extends AppCompatActivity {

    Button submitProcedureButton;
    private static final String TAG = "AddProcedureActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_procedure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        submitProcedureButton = (Button) findViewById(R.id.add_procedure_button);
        submitProcedureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This would be the actions started by onCreate in the get all activity

                // debugging - make sure I get here and the strings are what I want them to be
                Log.d(TAG, ((EditText) findViewById(R.id.procedure_name_input)).getText().toString());
                Log.d(TAG, ((EditText) findViewById(R.id.duration_input)).getText().toString());
                Log.d(TAG, ((EditText) findViewById(R.id.cost_input)).getText().toString());
                Log.d(TAG, ((EditText) findViewById(R.id.operator_input)).getText().toString());

                // Try to do post - copy paste from okhttp reference
                // https://raw.githubusercontent.com/square/okhttp/master/samples/guide/src/main/java/okhttp3/guide/PostExample.java
                // make the basic query parameters

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                OkHttpClient client = new OkHttpClient();
                HttpUrl reqUrl = HttpUrl.parse("https://final-197620.appspot.com/procedure");
                String json =
                        "{\"name\": \"" + ((EditText) findViewById(R.id.procedure_name_input)).getText().toString() + "\", " +
                                "\"duration\": " + Float.parseFloat(((EditText) findViewById(R.id.duration_input)).getText().toString()) + ", " +
                                "\"cost\": " + Float.parseFloat(((EditText) findViewById(R.id.cost_input)).getText().toString()) + ", " +
                                "\"operator\": \"" + ((EditText) findViewById(R.id.operator_input)).getText().toString() + "\"}";
                Log.d(TAG, json);
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(reqUrl)
                        .post(body)
                        .build();
                Log.d(TAG, "*** just before getting response ***");

                // call the request different - like in the example

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String r = response.body().string();
                        Log.d(TAG, r);

                        Intent next_activity =new Intent(AddProcedureActivity.this, MainActivity.class);
                        startActivity(next_activity);

                        /*
                        try {
                            // JSONObject j = new JSONObject(r);
                            // JSONArray items = j.getJSONArray("items");
                            JSONArray items = new JSONArray(r);
                            // List<Map<String,String>> clinics = new ArrayList<Map<String,String>>();
                            List<Map<String,String>> clinics = new ArrayList<>();
                            for(int i = 0; i < items.length(); i++){
                                Log.d(TAG, "***** B *****");

                                // HashMap<String, String> m = new HashMap<String, String>();
                                HashMap<String, String> m = new HashMap<>();

                                // put the name into the text view
                                m.put("name", items.getJSONObject(i).getString("name"));

                                // put the open time in the text view
                                String oh = items.getJSONObject(i).getString("openHour");
                                String om = items.getJSONObject(i).getString("openMinute");
                                if (om.equals("0"))
                                {
                                    om = "00";
                                }
                                String openTime = "Opens: " + oh + ":" + om;
                                m.put("openTime", openTime);

                                // put the close time in the text view
                                String ch = items.getJSONObject(i).getString("closeHour");
                                String cm = items.getJSONObject(i).getString("closeMinute");
                                if (cm.equals("0"))
                                {
                                    cm = "00";
                                }
                                String closeTime = "Closes: " + ch + ":" + cm;
                                m.put("closeTime", closeTime);

                                // put the location in the text view
                                String response_lat = items.getJSONObject(i).getString("lat");
                                String response_lon = items.getJSONObject(i).getString("lon");
                                String location = "Location.  Lat: " + response_lat + " Long:" + response_lon;
                                m.put("location", location);

                                Log.d(TAG, oh);
                                Log.d(TAG, items.getJSONObject(i).getString("name"));
                                // m.put("openHour",items.getJSONObject(i).getString("openHour"));
                                clinics.add(m);
                            }
                            final SimpleAdapter clinicAdapter = new SimpleAdapter(
                                    AllClinicsActivity.this,
                                    clinics,
                                    R.layout.clinic_item,
                                    new String[]{"name", "openTime", "closeTime", "location"},
                                    new int[]{R.id.clinic_name, R.id.open_time, R.id.close_time, R.id.clinic_location});
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((ListView)findViewById(R.id.list_all_clinics)).setAdapter(clinicAdapter);
                                }
                            });
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        */
                    }
                });

                // end of call the request different

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}

