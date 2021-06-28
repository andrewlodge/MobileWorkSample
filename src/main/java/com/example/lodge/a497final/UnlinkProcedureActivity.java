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


public class UnlinkProcedureActivity extends AppCompatActivity {

    private static final String TAG = "UnlinkProcedureActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlink_procedure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "***** A *****");

        // Get the id of the string to be modified
        String clinic_to_modify = "not_present";
        Intent clinic_intent = getIntent();
        if (clinic_intent.getExtras() != null)
        {
            clinic_to_modify = clinic_intent.getStringExtra("mod_id");
        }
        final String clinic_identifier = clinic_to_modify;
        Log.d(TAG, clinic_identifier);

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

                        // put the id in the text view
                        String response_id = items.getJSONObject(i).getString("id");
                        Log.d(TAG, response_id);
                        m.put("id", response_id);

                        // put these strings into the list view
                        procedures.add(m);

                    }
                    final SimpleAdapter unlinkProcedureAdapter = new SimpleAdapter(
                            UnlinkProcedureActivity.this,
                            procedures,
                            R.layout.unlink_procedure_item,
                            new String[]{"name", "duration", "cost", "operator", "id"},
                            new int[]{R.id.unlink_procedure_name, R.id.unlink_procedure_duration, R.id.unlink_procedure_cost, R.id.unlink_procedure_operator, R.id.unlink_procedure_id});
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ListView)findViewById(R.id.list_unlink_procedure)).setAdapter(unlinkProcedureAdapter);
                        }
                    });
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        });
        // OK after here
        final ListView lv = (ListView) findViewById(R.id.list_unlink_procedure);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked perform some action
                Log.d(TAG, "*** Heard a click on the list ***");
                Object map = lv.getItemAtPosition(position);
                String str = map.toString();
                char[] charArray = str.toCharArray();
                char[] procedureArray;
                procedureArray = new char[54];
                Integer procedurePosition = 0;
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
                        procedureArray[procedurePosition] = charArray[i];
                        procedurePosition++;
                    }
                    if (charArray[i] == '=' && charArray[i-1] == 'd' && charArray[i-2] == 'i')
                    {
                        readit = 1;
                    }
                }
                String procedure_identifier = new String(procedureArray);
                Log.d(TAG, "*** whole textview ***");
                Log.d(TAG, str);
                Log.d(TAG, "*** clinic string ***");
                Log.d(TAG, procedure_identifier);

                // Start the link call here
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                OkHttpClient linkOkHttpClient = new OkHttpClient();
                HttpUrl linkUrl = HttpUrl.parse("https://final-197620.appspot.com/clinic/");
                // String deletejson =
                //       "{\"id\": \"" + clinic_identifier + "\"}";
                // Log.d(TAG, deletejson);


                // This one works
                String json =
                        "{\"clinic\": \"" + clinic_identifier + "\", " +
                                "\"procedure\": \"" + procedure_identifier + "\", " +
                                "\"action\": \"remove\"}";

                Log.d(TAG, json);
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(linkUrl)
                        .put(body)
                        .build();
                Log.d(TAG, "*** just before getting response ***");

                // RequestBody deletebody = RequestBody.create(JSON, deletejson);

                linkOkHttpClient.newCall(request).enqueue(new Callback() {
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

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        Intent next_activity =new Intent(UnlinkProcedureActivity.this, MainActivity.class);
                        startActivity(next_activity);
                    }
                });

                // Finish the delete call here
                Log.d(TAG,  "**** Done with the delete call ****");
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
