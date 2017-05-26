package com.lsourtzo.app.tour_guide_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    private View mContentMain;

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawer;
    FragmentManager fragmentManager;

    LinearLayout linearView;

    String checkViewVisibility;
    String checkMapVisibility;

    //values to send on unic fragmentActivity
    String fName ="";
    String fjason1="";
    String fjason2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MultiDex.install(this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContentMain = LayoutInflater.from(this).inflate(R.layout.content_main, null);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // allow icons had true colors and not be as shadows ...
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = Fragment_Activity.class;
                try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        fName = getString(R.string.region);
        fjason1 = "Districts";
        fjason2 = "District";
        Bundle bundle = new Bundle();
        bundle.putString("mtitle", fName);
        bundle.putString("mjason1", fjason1);
        bundle.putString("mjason2", fjason2);
        // set Fragmentclass Arguments
        fragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    }


    //specify what to do when back button pressed
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //If drawable is opes close it ...
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (checkViewVisibility=="false"||checkMapVisibility=="true") {
                if (fragmentManager.getBackStackEntryCount() < 1) {
                    // Double click to exit app
                    if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                        super.onBackPressed();
                        return;
                    } else {
                        Toast.makeText(getBaseContext(), R.string.doubleClick, Toast.LENGTH_SHORT).show();
                    }
                    mBackPressed = System.currentTimeMillis();

                } else {
                    super.onBackPressed();
                }
                checkMapVisibility="false";
            }
            else{
                // call close view from fragment activity
                Fragment_Activity frag = (Fragment_Activity) getSupportFragmentManager().findFragmentById(R.id.flContent);
                frag.closeView ();
                //set visibility back to false because I just close the view
                checkViewVisibility="false";
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        if (id == R.id.nav_region) {
            fragmentClass = Fragment_Activity.class;
            fName = getString(R.string.region);
            fjason1 = "Districts";
            fjason2 = "District";
        } else if (id == R.id.nav_history) {
            fragmentClass = Fragment_Activity.class;
            fName = getString(R.string.history);
            fjason1 = "History";
            fjason2 = "Hist";
        } else if (id == R.id.nav_nature) {
            fragmentClass = Fragment_Activity.class;
            fName = getString(R.string.nature);
            fjason1 = "Nature";
            fjason2 = "Nat";
        } else if (id == R.id.nav_activities) {
            fragmentClass = Fragment_Activity.class;
            fName = getString(R.string.activities);
            fjason1 = "Activities";
            fjason2 = "Activitie";
        } else if (id == R.id.nav_sights) {
            fragmentClass = Fragment_Activity.class;
            fName = getString(R.string.sights);
            fjason1 = "Sights";
            fjason2 = "Sight";
        } else if (id == R.id.nav_products) {
            fragmentClass = Fragment_Activity.class;
            fName = getString(R.string.products);
            fjason1 = "Products";
            fjason2 = "Product";
        } else if (id == R.id.nav_map) {
            if (isInternetConected()) {
                fragmentClass = MapViewFragment.class;
            } else {
                Toast.makeText(getBaseContext(), R.string.noInternetConnection, Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_app_info) {
            fragmentClass = InfoFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!(id == R.id.nav_map && !isInternetConected())) {
            Bundle bundle = new Bundle();
            bundle.putString("mtitle", fName);
            bundle.putString("mjason1", fjason1);
            bundle.putString("mjason2", fjason2);
            // set Fragmentclass Arguments
            fragment.setArguments(bundle);
            fragmentManager = getSupportFragmentManager();
            //fragmentManager.popBackStackImmediate("0", 0);
            int count = fragmentManager.getBackStackEntryCount();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(String.valueOf(count)).commit();
            drawer.closeDrawer(GravityCompat.START, false);
        }
        if ((id != R.id.nav_map && id != R.id.nav_app_info && id != R.id.nav_app_info)) {

        }
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    public void setActionBarTitle(String title) {getSupportActionBar().setTitle(title); }

    // return if the detail view is on screan
    public void isViewVisible(String isVV) {checkViewVisibility = isVV;}

    // return if map is open
    public void isMapVisible(String isVV) {checkMapVisibility = isVV;}

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    // this method check if we are login in firebase
    public boolean isInternetConected() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }


}
