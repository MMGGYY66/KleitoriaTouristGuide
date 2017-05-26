package com.lsourtzo.app.tour_guide_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by lsourtzo on 14/05/2017.
 */

public class Startup_Fragment extends Fragment {

    public Startup_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main_screen, container, false);

        // Set title bar
        ((MainActivity) getActivity()) .setActionBarTitle(getString(R.string.nav_bar_title));

        return rootView;
    }


}
