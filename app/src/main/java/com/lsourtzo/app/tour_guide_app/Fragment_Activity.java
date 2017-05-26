package com.lsourtzo.app.tour_guide_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by lsourtzo on 14/05/2017.
 */

public class Fragment_Activity extends Fragment {

    LinearLayout dItemList;
    ScrollView dScrollView;
    ImageView dImageView;
    ImageView callMap;
    TextView dtitle;
    TextView dtext;
    Double mlatitude;
    Double mlongitude;
    int rimageInt;
    String mlocation;

    //Values from mainActivity
    String fName;
    String fjason1="1";
    String fjason2="2";

    public Fragment_Activity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.content_main, container, false);

        dScrollView = (ScrollView) rootView.findViewById(R.id.description_view);
        dItemList = (LinearLayout) rootView.findViewById(R.id.description_view_back);
        dImageView = (ImageView) rootView.findViewById(R.id.categorie_image2);
        callMap = (ImageView) rootView.findViewById(R.id.callMap);
        dtitle = (TextView) rootView.findViewById(R.id.title_text_view2);
        dtext = (TextView) rootView.findViewById(R.id.default_text_view2);

        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle(fName);

        // Send to Main Activity if Layer is visible
        ((MainActivity) getActivity()).isViewVisible("false");

        // check if there is called by main menu
        Bundle bundle = this.getArguments();
        if (bundle == null) {
            fName=" ";
            fjason1=" ";
            fjason2=" ";
        } else {

            fName=getArguments().getString("mtitle");
            fjason1=getArguments().getString("mjason1");
            fjason2=getArguments().getString("mjason2");
        }

        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle(fName);

        //Get Data From Text Resource File Contains Json Data.
        InputStream inputStream = getResources().openRawResource(R.raw.data);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Create a list
        final ArrayList<List> lists = new ArrayList<List>();
        // Parsing json file to take data
        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // Parse the data into jsonobject to get original data in form of json.
            JSONObject jObject = new JSONObject(
                    byteArrayOutputStream.toString());
            Log.d ("test","jason "+fName+" "+fjason1+" "+fjason2);
            JSONObject jObjectResult = jObject.getJSONObject(fjason1);
            JSONArray jArray = jObjectResult.getJSONArray(fjason2);

            String title;
            String text;
            String image;
            String sorttext;

            ArrayList<String[]> data = new ArrayList<String[]>();
            for (int i = 0; i < jArray.length(); i++) {
                // check for system language and get the correct text's
                if (Locale.getDefault().getLanguage().contentEquals("el")) {
                    title = jArray.getJSONObject(i).getString("title_el");
                    text = jArray.getJSONObject(i).getString("text_el") + "\n\n\n\n";
                } else {
                    title = jArray.getJSONObject(i).getString("title");
                    text = jArray.getJSONObject(i).getString("text") + "\n\n\n\n";
                }

                mlatitude = Double.parseDouble(jArray.getJSONObject(i).getString("latitude"));
                mlongitude = Double.parseDouble(jArray.getJSONObject(i).getString("longitude"));


                image = jArray.getJSONObject(i).getString("image");
                // convert string image file name to id
                int id = getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
                // check if there is Image
                if (image.equals("0")) {
                    //cut text to fit in menu
                    sorttext = text.substring(0, 90) + " ...";
                    lists.add(new List(title, text, sorttext, mlatitude, mlongitude));
                } else {
                    //cut text to fit in menu
                    sorttext = text.substring(0, 60) + " ...";
                    lists.add(new List(title, text, sorttext, id, mlatitude, mlongitude));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create an {@link ListAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        ListAdapter madapter = new ListAdapter(getActivity(), lists);

        // Find the {@link GridView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        GridView gridView = (GridView) rootView.findViewById(R.id.list);

        // Make the {@link GridView} use the {@link WordAdapter} we created above, so that the
        // {@link gridView} will display list items for each {@link List} in the list.
        gridView.setAdapter(madapter);

        // Set a click listener to play the audio when the list item is clicked on
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Get the {@link List} object at the given position the user clicked on
                List list = lists.get(position);
                if (list.hasImage()) {
                    dImageView.setVisibility(View.VISIBLE);
                    dImageView.setImageResource(list.getImageResourceId());
                    // I will use this to restore image after activity restart
                    rimageInt = list.getImageResourceId();

                } else {
                    dImageView.setVisibility(View.GONE);
                    // when is 0 there is no picture
                    rimageInt = 0;
                }
                dtitle.setText(list.getTitle());
                dtext.setText(list.getTextd());
                mlatitude = list.getLatitude();
                mlongitude = list.getLongitude();
                mlocation = list.getTitle();

                callMap.setVisibility(View.VISIBLE);
                dItemList.setVisibility(View.VISIBLE);
                // Send to Main Activity if Layer is visible
                ((MainActivity) getActivity()).isViewVisible("true");

                if (mlatitude == 0 && mlongitude == 0) {
                    callMap.setVisibility(View.GONE);callMap.setVisibility(View.GONE);
                }
            }
        });


        // Find the View that shows the family category
        final ImageView callmap = (ImageView) rootView.findViewById(R.id.callMap);
        // Set a click listener on that View
        callmap.setOnClickListener(new View.OnClickListener()

        {
            // The code in this method will be executed when the homebutton will pressed
            @Override
            public void onClick(View view) {
                if (isInternetConected()) {
                    ((MainActivity) getActivity()).isViewVisible("false");
                    MapViewFragment fr = new MapViewFragment();
                    FragmentManager fragmentManager = getFragmentManager();

                    // send the latitudes
                    Bundle bundle = new Bundle();
                    bundle.putDouble("latitude", mlatitude);
                    bundle.putDouble("longitude", mlongitude);
                    bundle.putString("placename", mlocation);
                    // set Fragmentclass Arguments
                    fr.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //fragmentManager.popBackStackImmediate("0", 0);
                    int count = fragmentManager.getBackStackEntryCount();
                    fragmentTransaction.replace(R.id.flContent, fr);
                    fragmentTransaction.addToBackStack(String.valueOf(count)).commit();
                } else {
                    Toast.makeText(getActivity(), R.string.noInternetConnection, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }


    // this method check if we are login in firebase
    public boolean isInternetConected() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }

    public void closeView () {
        dItemList.setVisibility(View.GONE);
        callMap.setVisibility(View.GONE);
    }

}
