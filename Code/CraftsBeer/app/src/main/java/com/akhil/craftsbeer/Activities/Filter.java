package com.akhil.craftsbeer.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.akhil.craftsbeer.Adapters.FilterAdapter;
import com.akhil.craftsbeer.Properties.ApplicationProperties;
import com.akhil.craftsbeer.R;

public class Filter extends AppCompatActivity {

    FilterAdapter filterAdapter = null;
    ListView styleList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setTitle("Filter by beer type");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        styleList = (ListView) findViewById(R.id.settingsList);
        filterAdapter = new FilterAdapter(ApplicationProperties.getInstance().getStyleList());
        styleList.setAdapter(filterAdapter);
        setSupportActionBar(toolbar);
    }
}
