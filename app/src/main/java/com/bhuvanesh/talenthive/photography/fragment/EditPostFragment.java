package com.bhuvanesh.talenthive.photography.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.util.THLoggerUtil;
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
    private FloatingActionButton locationFAButton;
    private boolean locationFlag=false;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public static EditPostFragment newInstance(){return new EditPostFragment();}

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
        atTextView= (TextView) view.findViewById(R.id.textview_at);
        locationTextView= (TextView) view.findViewById(R.id.textview_location);
        locationFAButton= (FloatingActionButton) view.findViewById(R.id.fab_location);
        locationFAButton.setOnClickListener(new View.OnClickListener() {
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
                THLoggerUtil.debug("hh", "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                locationFlag=false; atTextView.setVisibility(View.GONE);
                locationTextView.setVisibility(View.GONE);
                Toast.makeText(getActivity(),getString(R.string.msg_error),Toast.LENGTH_SHORT ).show();
                THLoggerUtil.debug("hh","Status"+status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }
}
