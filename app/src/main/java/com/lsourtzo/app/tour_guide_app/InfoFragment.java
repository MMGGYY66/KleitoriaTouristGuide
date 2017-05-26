package com.lsourtzo.app.tour_guide_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import static com.lsourtzo.app.tour_guide_app.R.layout.activity_info;
import static com.lsourtzo.app.tour_guide_app.R.layout.activity_map;


/**
 * Created by lsourtzo on 14/05/2017.
 */

public class InfoFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(activity_info, container, false);

        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.app_info));

        return rootView;
    }
}
