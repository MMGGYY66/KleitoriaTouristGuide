package com.lsourtzo.app.tour_guide_app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lsourtzo on 14/05/2017.
 */

public class ListAdapter extends ArrayAdapter<List> {

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists
     */
    public ListAdapter(Activity context, ArrayList<List> word){
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, word);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent ) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_categories, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        List currentWord = getItem(position);

        // Find the TextView in the categories_list.xml layout with default language
        TextView titleText = (TextView) listItemView.findViewById(R.id.title_text_view);
        // Get default word and set this text on the name TextView
        titleText.setText(currentWord.getTitle());

        // Find the TextView in the categories_list.xml  layout with the ID version_number
        TextView sortText = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        sortText.setText(currentWord.getSortText());

        // Find the ImageView in the categories_list.xml  layout with the ID list_item_icon
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.categorie_image);

        Double mlatitude = currentWord.getLatitude();
        Double mlongitude = currentWord.getLongitude();

        // Get the image resource ID from the current  object and
        // set the image to iconView
        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);}
        else
        {
            imageView.setVisibility(View.GONE);
        }

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView

        return listItemView;
    }
}