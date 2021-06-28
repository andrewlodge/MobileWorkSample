package com.example.lodge.a497final;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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
import android.widget.AdapterView;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LinkClinicActivity extends AppCompatActivity {

    private static final String TAG = "LinkClinicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_clinic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "***** A *****");

        OkHttpClient mOkHttpClient = new OkHttpClient();
        HttpUrl reqUrl = HttpUrl.parse("https://final-197620.appspot.com/clinics");
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

                        // put the procedures in the text view
                        String procedures = items.getJSONObject(i).getString("procedures");
                        m.put("procedures", procedures);

                        // put the id in the text view
                        String response_id = items.getJSONObject(i).getString("id");
                        Log.d(TAG, response_id);
                        m.put("id", response_id);

                        Log.d(TAG, oh);
                        Log.d(TAG, items.getJSONObject(i).getString("name"));
                        // m.put("openHour",items.getJSONObject(i).getString("openHour"));
                        clinics.add(m);
                    }
                    final SimpleAdapter linkClinicAdapter = new SimpleAdapter(
                            LinkClinicActivity.this,
                            clinics,
                            R.layout.link_clinic_item,
                            new String[]{"name", "openTime", "closeTime", "location", "procedures", "id"},
                            new int[]{R.id.link_name, R.id.link_open, R.id.link_close, R.id.link_location, R.id.link_procedures_display, R.id.link_id});
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ListView)findViewById(R.id.list_link_clinic)).setAdapter(linkClinicAdapter);
                        }
                    });
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // OK after here
        final ListView lv = (ListView) findViewById(R.id.list_link_clinic);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked perform some action
                Log.d(TAG, "*** Heard a click on the list ***");
                Object map = lv.getItemAtPosition(position);
                String str = map.toString();
                char[] charArray = str.toCharArray();
                char[] clinicArray;
                clinicArray = new char[50];
                Integer clinicPosition = 0;
                Integer readit = 0;
                for (int i = 0; i < charArray.length; i++)
                {
                    // chop up the array to get the characters after id= and before the commma
                    // this feels pretty low tech, but i am struggling to parse this response object
                    if(charArray[i] == ',')
                    {
                        readit = 0;
                    }
                    if(readit == 1)
                    {
                        clinicArray[clinicPosition] = charArray[i];
                        clinicPosition++;
                    }
                    if (charArray[i] == '=' && charArray[i-1] == 'd' && charArray[i-2] == 'i')
                    {
                        readit = 1;
                    }
                }
                String clinic_identifier = new String(clinicArray);
                Log.d(TAG, "*** whole textview in modify ***");
                Log.d(TAG, str);
                Log.d(TAG, "*** clinic string in modify ***");
                Log.d(TAG, clinic_identifier);

                // REFERENCE
                // https://www.101apps.co.za/articles/passing-data-between-activities.html
                // https://stackoverflow.com/questions/4233873/how-do-i-get-extra-data-from-intent-on-android
                // I used these for the CS496 SQL/Location assignment and copied to this one
                Intent j=new Intent(LinkClinicActivity.this, LinkProcedureActivity.class);
                j.putExtra("mod_id", clinic_identifier);
                startActivity(j);

                // Finish the delete call here
                Log.d(TAG,  "**** Done with the modify call; shouldn't see this ****");
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

