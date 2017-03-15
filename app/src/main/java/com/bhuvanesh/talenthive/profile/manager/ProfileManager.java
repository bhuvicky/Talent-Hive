package com.bhuvanesh.talenthive.profile.manager;


import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.WebServiceManager;
import com.bhuvanesh.talenthive.model.Profile;

import java.util.List;

public class ProfileManager extends WebServiceManager {

    interface OnGetProfileManager {
        void onGetProfileListSuccess(List<Profile> response);
        void onGetProfileListError(THException exception);
    }
}
