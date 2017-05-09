package com.bhuvanesh.talenthive.profile.manager;


import com.bhuvanesh.talenthive.BaseResponse;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.WebServiceManager;
import com.bhuvanesh.talenthive.model.Profile;
import com.bhuvanesh.talenthive.profile.manager.operation.GetProfileOperation;
import com.bhuvanesh.talenthive.profile.manager.operation.UpdateProfileOperation;
import com.google.gson.Gson;

import java.util.List;

public class ProfileManager extends WebServiceManager {

    public interface OnGetProfileManager {
        void onGetProfileListSuccess(List<Profile> response);
        void onGetProfileListError(THException exception);
    }

    public interface OnUpdateProfileManager {
        void onUpdateProfileSuccess(BaseResponse response);
        void onUpdateProfileError(THException exception);
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

    public void updateProfile(Profile profile, final OnUpdateProfileManager listener) {
        UpdateProfileOperation operation = new UpdateProfileOperation(getHeaders(), new Gson().toJson(profile),
                new UpdateProfileOperation.OnUpdateProfileOperation() {
                    @Override
                    public void onUpdateProfileSuccess(BaseResponse response) {
                        listener.onUpdateProfileSuccess(response);
                    }

                    @Override
                    public void onUpdateProfileError(THException exception) {
                        listener.onUpdateProfileError(exception);

                    }
                });
        operation.addToRequestQueue();
    }

}
