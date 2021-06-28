package com.example.lodge.a497final;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView ClinicsItem = findViewById(R.id.display_clinics);
        ClinicsItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllClinicsActivity.class);
                startActivity(intent);
            }
        });

        TextView ProceduresItem = findViewById(R.id.display_procedures);
        ProceduresItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllProceduresActivity.class);
                startActivity(intent);
            }
        });

        TextView AddClinicItem = findViewById(R.id.add_clinic);
        AddClinicItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddClinicActivity.class);
                startActivity(intent);
            }
        });

        TextView AddProcedureItem = findViewById(R.id.add_procedure);
        AddProcedureItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProcedureActivity.class);
                startActivity(intent);
            }
        });

        TextView ModifyClinicItem = findViewById(R.id.modify_clinic);
        ModifyClinicItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ModifyClinicActivity.class);
                startActivity(intent);
            }
        });

        TextView ModifyProcedureItem = findViewById(R.id.modify_procedure);
        ModifyProcedureItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ModifyProcedureActivity.class);
                startActivity(intent);
            }
        });

        TextView DeleteClinicItem = findViewById(R.id.delete_clinic);
        DeleteClinicItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteClinicActivity.class);
                startActivity(intent);
            }
        });

        TextView DeleteProcedureItem = findViewById(R.id.delete_procedure);
        DeleteProcedureItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteProcedureActivity.class);
                startActivity(intent);
            }
        });

        TextView LinkProcedureItem = findViewById(R.id.link_procedure);
        LinkProcedureItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LinkClinicActivity.class);
                startActivity(intent);
            }
        });

        TextView UnlinkProcedureItem = findViewById(R.id.unlink_procedure);
        UnlinkProcedureItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UnlinkClinicActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
