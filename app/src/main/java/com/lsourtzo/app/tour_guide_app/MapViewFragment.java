package com.lsourtzo.app.tour_guide_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.lsourtzo.app.tour_guide_app.R.layout.activity_map;


/**
 * Created by lsourtzo on 14/05/2017.
 */

public class MapViewFragment extends Fragment implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    MapView mMapView;
    private GoogleMap mMap;
    Double mlatitude = 0.0;
    Double mlongitude = 0.0;
    String mPlaceName = "Kleitoria";
    Marker mLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(activity_map, container, false);

        ((MainActivity) getActivity()).isMapVisible("true");


        // check if there is called by main menu
        Bundle bundle = this.getArguments();
        if (bundle == null) {
            mlatitude = 37.896;
            mlongitude = 22.123;
            mPlaceName = "Kleitoria";
        } else {
            mlatitude = getArguments().getDouble("latitude");
            mlongitude = getArguments().getDouble("longitude");
            mPlaceName = getArguments().getString("placename");
        }


        if (mlatitude == 0 && mlongitude == 0) {
            mlatitude = 37.896;
            mlongitude = 22.123;
            mPlaceName = "Kleitoria";
        }

        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.map));

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        // set location
        FragmentManager fm1 = getFragmentManager();
        CameraPosition cp = new CameraPosition.Builder()
                .target(new LatLng(mlatitude, mlongitude))
                .zoom(12)
                .build();

        SupportMapFragment mapFragment = SupportMapFragment.newInstance(new GoogleMapOptions().camera(cp));
        fm1.beginTransaction().replace(R.id.rl_map, mapFragment).commit();

        // this line it's for synchronize the map and show icon
        mapFragment.getMapAsync(this);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        final LatLng Location = new LatLng(mlatitude, mlongitude);
        mLocation = mMap.addMarker(new MarkerOptions()
                .title(mPlaceName)
                .position(Location));
        mLocation.showInfoWindow();


        // Set a listener for marker click.
        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);

    }

    /**
     * Called when the user clicks a marker.
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        //Log.d("test","click");
        // something when is clicked ...

        return false;
    }



}
