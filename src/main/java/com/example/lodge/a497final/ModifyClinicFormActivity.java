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



public class ModifyClinicFormActivity extends AppCompatActivity {

    Button submitClinicButton;
    private static final String TAG = "ModifyClinicFormActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_clinic_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        submitClinicButton = (Button) findViewById(R.id.modify_clinic_button);
        submitClinicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // https://raw.githubusercontent.com/square/okhttp/master/samples/guide/src/main/java/okhttp3/guide/PostExample.java
                // make the basic query parameters

                // Get the id of the string to be modified
                String id_to_modify = "not_present";
                Intent i = getIntent();
                if (i.getExtras() != null)
                {
                    id_to_modify = i.getStringExtra("mod_id");
                }

                Log.d(TAG, id_to_modify);

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                OkHttpClient client = new OkHttpClient();
                HttpUrl reqUrl = HttpUrl.parse("https://final-197620.appspot.com/clinic/");

                // this is the beginning of the string
                String json = "{\"id\": \"" + id_to_modify + "\"";

                // if present in the form, append name
                try
                {
                    json = json + ", \"name\": \"" + ((EditText) findViewById(R.id.modify_clinic_name_input)).getText().toString() + "\"";
                }
                catch(Exception e)
                {
                    Log.d(TAG, "No name in modification");
                }

                // if present in the form, append open hour
                try
                {
                    json = json + ", \"openHour\": " + Integer.parseInt(((EditText) findViewById(R.id.modify_open_hour_input)).getText().toString());
                }
                catch(Exception e)
                {
                    Log.d(TAG, "No open_hour in modification");
                }

                // if present in the form, append open minute
                try
                {
                    json = json + ", \"openMinute\": " + Integer.parseInt(((EditText) findViewById(R.id.modify_open_minute_input)).getText().toString());
                }
                catch(Exception e)
                {
                    Log.d(TAG, "No open_minute in modification");
                }

                // if present in the form, append close hour
                try
                {
                    json = json + ", \"closeHour\": " + Integer.parseInt(((EditText) findViewById(R.id.modify_close_hour_input)).getText().toString());
                }
                catch(Exception e)
                {
                    Log.d(TAG, "No close_hour in modification");
                }

                // if present in the form, append close minute
                try
                {
                    json = json + ", \"closeMinute\": " + Integer.parseInt(((EditText) findViewById(R.id.modify_close_minute_input)).getText().toString());
                }
                catch(Exception e)
                {
                    Log.d(TAG, "No close_minute in modification");
                }

                // if present in the form, append latitude
                try
                {
                    json = json + ", \"lat\": " + Float.parseFloat(((EditText) findViewById(R.id.modify_latitude_input)).getText().toString());
                }
                catch(Exception e)
                {
                    Log.d(TAG, "No close_minute in modification");
                }

                // if present in the form, append longitude
                try
                {
                    json = json + ", \"lon\": " + Float.parseFloat(((EditText) findViewById(R.id.modify_longitude_input)).getText().toString());
                }
                catch(Exception e)
                {
                    Log.d(TAG, "No close_minute in modification");
                }

                // append the closing bracket
                json = json + "}";

                /*
                // This one works
                String json =
                        "{\"name\": \"" + ((EditText) findViewById(R.id.modify_clinic_name_input)).getText().toString() + "\", " +
                                "\"id\": \"" + id_to_modify + "\", " +
                                "\"openHour\": " + Integer.parseInt(((EditText) findViewById(R.id.modify_open_hour_input)).getText().toString()) + ", " +
                                "\"openMinute\": " + Integer.parseInt(((EditText) findViewById(R.id.modify_open_minute_input)).getText().toString()) + ", " +
                                "\"closeHour\": " + Integer.parseInt(((EditText) findViewById(R.id.modify_close_hour_input)).getText().toString()) + ", " +
                                "\"closeMinute\": " + Integer.parseInt(((EditText) findViewById(R.id.modify_close_minute_input)).getText().toString()) + ", " +
                                "\"lat\": " + Float.parseFloat(((EditText) findViewById(R.id.modify_latitude_input)).getText().toString()) + ", " +
                                "\"lon\": " + Float.parseFloat(((EditText) findViewById(R.id.modify_longitude_input)).getText().toString()) + "}";
                */
                Log.d(TAG, json);
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(reqUrl)
                        .patch(body)
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

                        Intent next_activity =new Intent(ModifyClinicFormActivity.this, MainActivity.class);
                        startActivity(next_activity);
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
