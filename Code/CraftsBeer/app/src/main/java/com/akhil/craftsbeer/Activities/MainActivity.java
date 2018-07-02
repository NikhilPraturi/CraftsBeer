package com.akhil.craftsbeer.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.akhil.craftsbeer.DataModel.Beer;
import com.akhil.craftsbeer.Properties.ApplicationProperties;
import com.akhil.craftsbeer.R;
import com.akhil.craftsbeer.SharedPreferences.SharedValues;
import com.akhil.craftsbeer.Utilities.HttpUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Dialog dialog;

    FragmentManager fragmentManager;

    ApplicationProperties m_applicationProperties = null;

    private boolean m_savedInstance;
    public Handler m_handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ConnectivityManager connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(MainActivity.this).setTitle("No internet!").setMessage("Please connect to the internet.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }).show();
                }
            });
        }

        m_applicationProperties = ApplicationProperties.getInstance();
        SharedValues.getINSTANCE().setContext(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            m_savedInstance = false;
        }
        new DataTransport().execute();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            //SharedValues.getINSTANCE().setBeerCartList(ApplicationProperties.getInstance().getCartList());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedValues.getINSTANCE().setBeerCartList(ApplicationProperties.getInstance().getCartList());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            if (!m_savedInstance) {
                setTitle("Crafts Beer");
                Fragment beerList = new BeerListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", "all");
                beerList.setArguments(bundle);
                replaceFragment(beerList, false, "");
            }
        } else if (id == R.id.nav_cart) {
            setTitle("Beer Cart");
            Fragment beerList = new BeerListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", "cart");
            beerList.setArguments(bundle);
            replaceFragment(beerList, false, "");

        } else if (id == R.id.nav_filter) {
            Toast.makeText(this, "Filter is yet to be implemented.", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private class DataTransport extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpUtilities httpUtilities = new HttpUtilities();
            String jsonString = httpUtilities.callService("http://starlord.hackerearth.com/beercraft");
            Set<String> filterSet = new HashSet<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showProgress();
                }
            });
            if (jsonString != null) {
                try {
                    JSONArray jsonObject = new JSONArray(jsonString);
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject obj = jsonObject.getJSONObject(i);
                        String abv = obj.getString("abv");
                        String ibu = obj.getString("ibu");
                        String id = obj.getString("id");
                        String name = obj.getString("name");
                        String style = obj.getString("style");
                        String ounces = obj.getString("ounces");
                        if (abv.isEmpty()) {
                            abv = "0.0";
                        }

                        filterSet.add(style);

                        Beer beer = new Beer(abv, ibu, id, name, style, ounces, 0);

                        m_applicationProperties.getBeerList().add(beer);


                    }
                    int size = filterSet.size();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (SharedValues.getINSTANCE().getBeerCartList() != null) {
                ApplicationProperties.getInstance().getCartList().addAll(SharedValues.getINSTANCE().getBeerCartList());
                for (Beer beer : ApplicationProperties.getInstance().getCartList()) {
                    for (int i = 0; i < ApplicationProperties.getInstance().getBeerList().size(); i++) {
                        if (ApplicationProperties.getInstance().getBeerList().get(i).getId().equals(beer.getId())) {
                            ApplicationProperties.getInstance().getBeerList().get(i).setAddedTocart(1);
                            break;
                        }
                    }
                }
                SharedValues.getINSTANCE().setBeerCartList(null);
            }
            if (!m_savedInstance) {
                Fragment beerList = new BeerListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", "all");
                beerList.setArguments(bundle);
                addFragment(beerList, false, "");
            }
            dismissProgress();

        }
    }

    public void addFragment(android.support.v4.app.Fragment fragment, boolean addToBackStack, String tag) {

        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container_frame, fragment);
            fragmentTransaction.commit();

        }
    }

    public void replaceFragment(android.support.v4.app.Fragment fragment, boolean addToBackStack, String tag) {

        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_frame, fragment);
            fragmentTransaction.commit();
        }
    }

    public void showProgress() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.Theme_Transparent);
            dialog.setContentView(R.layout.progress_bar_layout);
            dialog.setCancelable(false);
        }
        dialog.show();
    }

    public void dismissProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("Crafts Beer");
    }
}
