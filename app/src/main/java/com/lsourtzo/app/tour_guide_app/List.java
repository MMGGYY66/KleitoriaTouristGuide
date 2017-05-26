package com.lsourtzo.app.tour_guide_app;

/**
 * Created by lsourtzo on 14/05/2017.
 */

public class List {

    private String mTitle;
    private String mTextd;
    private String mSortText;
    private Double mlatitude;
    private Double mlongitude;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED  = -1;

    // title is for title , textd is for the main text, sorttext is for sortversion text in button and imageId its for image
    public List(String title, String textd,String sorttext, int ImageResourceId,Double latitude,Double longitude ){
        mTitle = title;
        mTextd = textd;
        mSortText = sorttext;
        mlatitude = latitude;
        mlongitude = longitude;
        mImageResourceId = ImageResourceId;
    }

    // title is for title , textd is for the main text, sorttext is for sortversion text in button
    public List(String title, String textd, String sorttext,Double latitude,Double longitude ){
        mTitle = title;
        mTextd = textd;
        mSortText = sorttext;
        mlatitude = latitude;
        mlongitude = longitude;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getTextd(){return mTextd; }

    public String getSortText(){return mSortText; }

    public Double getLatitude(){return mlatitude; }

    public Double getLongitude(){return mlongitude; }

    public int getImageResourceId(){
        return mImageResourceId;
    }

    // I use this one to return if there is Image or not ...
    public boolean hasImage (){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}
