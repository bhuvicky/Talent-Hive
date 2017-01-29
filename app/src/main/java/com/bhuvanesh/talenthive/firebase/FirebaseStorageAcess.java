package com.bhuvanesh.talenthive.firebase;


import android.content.Context;
import android.net.Uri;

import com.bhuvanesh.talenthive.util.THLoggerUtil;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;

public class FirebaseStorageAcess {

   private FirebaseStorage firebaseStorage;
    private Context context;
    public FirebaseStorageAcess(Context context)
    {
        this.context=context;
        firebaseStorage = FirebaseStorage.getInstance();
    }
    public UploadTask uploadPhoto(Uri photoUrl, String dateOfPhoto){
        final String[] url = new String[1];
        // Create a storage reference from our app
        StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://talenthive-37f51.appspot.com");
        String fileName="photo"+dateOfPhoto+".jpg";
        StorageReference photographyRef = storageRef.child("photography");
        StorageReference photoRef = photographyRef.child(fileName);
        UploadTask uploadPhoto = null;
        try {
            uploadPhoto = photoRef.putStream(context.getContentResolver().openInputStream(photoUrl));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            THLoggerUtil.debug("hh","filenotfound"+photoUrl);
        }
        return uploadPhoto;
    }

    public StorageReference downloadPdf(String photoUrl)
    {
        return firebaseStorage.getReferenceFromUrl(photoUrl);
    }
}