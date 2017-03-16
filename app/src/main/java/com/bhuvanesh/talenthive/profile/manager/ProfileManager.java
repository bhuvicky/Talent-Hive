package com.bhuvanesh.talenthive.profile.manager;


import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.WebServiceManager;
import com.bhuvanesh.talenthive.model.Profile;
import com.bhuvanesh.talenthive.profile.manager.operation.GetProfileOperation;

import java.util.List;

public class ProfileManager extends WebServiceManager {

    public interface OnGetProfileManager {
        void onGetProfileListSuccess(List<Profile> response);
        void onGetProfileListError(THException exception);
    }

    public void getProfileList(String selfProfileId, String other, final OnGetProfileManager listener) {
        GetProfileOperation operation = new GetProfileOperation(selfProfileId, other, getHeaders(), new GetProfileOperation.OnGetProfileOperation() {
            @Override
            public void onGetProfileListSuccess(List<Profile> response) {
                if (listener != null)
                    listener.onGetProfileListSuccess(response);
            }

            @Override
            public void onGetProfileListError(THException exception) {
                if (listener != null)
                    listener.onGetProfileListError(exception);
            }
        });
        operation.addToRequestQueue();
    }

}
