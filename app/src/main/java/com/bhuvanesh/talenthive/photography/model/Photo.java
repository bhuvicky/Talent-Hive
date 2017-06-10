package com.bhuvanesh.talenthive.photography.model;


import android.net.Uri;

public class Photo {
    public String photoID;
    public String titleDescription;
    public long lastModifiedTime;
    public String location;
    public String photoURL;
    transient public Uri photoUri;
}
