package com.bhuvanesh.talenthive.photography.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.THApplication;
import com.bhuvanesh.talenthive.account.model.UserDetails;
import com.bhuvanesh.talenthive.constant.IntentConstant;
import com.bhuvanesh.talenthive.dashboard.activity.DashboardActivity;
import com.bhuvanesh.talenthive.photography.view.AutoFitImageView;
import com.bhuvanesh.talenthive.photography.view.AutoFitNetworkImageView;
import com.bhuvanesh.talenthive.widget.CircularNetworkImageView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class EditPostFragment extends BaseFragment{
    private TextView profileNameTextView,atTextView,locationTextView;
    private EditText captionEditText;
    private Button locationButton;
    private CircularNetworkImageView circularNetworkImageViewProfile;
    private AutoFitImageView postPreviewImageView;
    private boolean locationFlag=false;
    private Uri previewImgURI;
    private UserDetails userDetails;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public static EditPostFragment newInstance(Uri uri){
        EditPostFragment editPostFragment=new EditPostFragment();
        editPostFragment.previewImgURI=uri;
        return editPostFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_edit_post,container,false);
        setHasOptionsMenu(true);
         if(((BaseActivity)getActivity()).getSupportActionBar()!=null)
         {
             ((BaseActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         }
        return  view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
                inflater.inflate(R.menu.edit_post_menu,menu);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.msg_post_to);
        profileNameTextView= (TextView) view.findViewById(R.id.textview_name_of_profile);
        circularNetworkImageViewProfile=(CircularNetworkImageView)view.findViewById(R.id.cnv_post_profile);

        THApplication thApplication=THApplication.getInstance();
        userDetails=thApplication.getUserDetails();
        profileNameTextView.setText(userDetails.name);
        circularNetworkImageViewProfile.setImageUrl(userDetails.profilePicUrl,thApplication.getImageLoader());
        atTextView= (TextView) view.findViewById(R.id.textview_at);
        captionEditText= (EditText) view.findViewById(R.id.edittext_edit_post);
        locationTextView= (TextView) view.findViewById(R.id.textview_location);
        postPreviewImageView= (AutoFitImageView) view.findViewById(R.id.imageview_post_preview);
        postPreviewImageView.setImageURI(previewImgURI);
        postPreviewImageView.setAspectRatio(9,16);
        locationButton= (Button) view.findViewById(R.id.button_location);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)   {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                  } catch (GooglePlayServicesNotAvailableException e) {
                 }
            }
        });
        captionEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (view == captionEditText) {
                    if (hasFocus) {
                        // Open keyboard
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(captionEditText, InputMethodManager.SHOW_FORCED);
                    } else {
                        // Close keyboard
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(captionEditText.getWindowToken(), 0);
                    }
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PLACE_AUTOCOMPLETE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK) {
                if(!locationFlag){
                    locationFlag=true;atTextView.setVisibility(View.VISIBLE);
                    locationTextView.setVisibility(View.VISIBLE);
                }
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                locationTextView.setText(place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                locationFlag=false; atTextView.setVisibility(View.GONE);
                locationTextView.setVisibility(View.GONE);
                Toast.makeText(getActivity(),getString(R.string.msg_error),Toast.LENGTH_SHORT ).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_post:
//                Photo photo=new Photo();
//                photo.titleDescription=captionEditText.getText().toString();
//                photo.lastModifiedTime=System.currentTimeMillis();
//                photo.location=locationTextView.getText().toString();
                Intent intent=new Intent(getActivity(), DashboardActivity.class);
                intent.putExtra(IntentConstant.URI,previewImgURI);
                intent.putExtra(IntentConstant.TITLE,captionEditText.getText().toString());
                intent.putExtra(IntentConstant.TIME,System.currentTimeMillis());
                intent.putExtra(IntentConstant.LOCATION,locationTextView.getText().toString());
                startActivity(intent);
                getActivity().finish();
                 //         replace(R.id.filter_container,PhotoFeedFragment.newInstance(previewImgURI,photo));
                break;
            case android.R.id.home:
                pop();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
