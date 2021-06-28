package com.example.lodge.a497final;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AllProceduresActivity extends AppCompatActivity {

    private static final String TAG = "AllProceduresActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_procedures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "***** A *****");

        OkHttpClient mOkHttpClient = new OkHttpClient();
        HttpUrl reqUrl = HttpUrl.parse("https://final-197620.appspot.com/procedures");
        Request request = new Request.Builder()
                .url(reqUrl)
                .build();

        // OK to here
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String r = response.body().string();
                Log.d(TAG, r);
                try {
                    // JSONObject j = new JSONObject(r);
                    // JSONArray items = j.getJSONArray("items");
                    JSONArray items = new JSONArray(r);
                    // List<Map<String,String>> clinics = new ArrayList<Map<String,String>>();
                    List<Map<String,String>> procedures = new ArrayList<>();
                    for(int i = 0; i < items.length(); i++){
                        Log.d(TAG, "***** B *****");

                        // HashMap<String, String> m = new HashMap<String, String>();
                        HashMap<String, String> m = new HashMap<>();

                        // put the name into the text view
                        m.put("name", items.getJSONObject(i).getString("name"));

                        // put the duration into the text view
                        m.put("duration", items.getJSONObject(i).getString("duration"));

                        // put the cost into the text view
                        m.put("cost", items.getJSONObject(i).getString("cost"));

                        // put the operator into the text view
                        m.put("operator", items.getJSONObject(i).getString("operator"));

                        // put these strings into the list view
                        procedures.add(m);
                    }
                    final SimpleAdapter procedureAdapter = new SimpleAdapter(
                            AllProceduresActivity.this,
                            procedures,
                            R.layout.procedure_item,
                            new String[]{"name", "duration", "cost", "operator"},
                            new int[]{R.id.procedure_name, R.id.procedure_duration, R.id.procedure_cost, R.id.procedure_operator});
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ListView)findViewById(R.id.list_all_procedures)).setAdapter(procedureAdapter);
                        }
                    });
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        });
        // OK after here

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
