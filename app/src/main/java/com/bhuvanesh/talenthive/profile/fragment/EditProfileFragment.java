package com.bhuvanesh.talenthive.profile.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.BaseResponse;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.RunTimePermissionFragment;
import com.bhuvanesh.talenthive.THApplication;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.model.Profile;
import com.bhuvanesh.talenthive.profile.manager.ProfileManager;
import com.bhuvanesh.talenthive.widget.CircularNetworkImageView;

/**
 * Created by bhuvanesh on 12-03-2017.
 */

public class EditProfileFragment extends RunTimePermissionFragment implements TextWatcher {

    public static final int PICK_GALLERY_IMAGE_REQUEST_CODE = 100;
    public static final int READ_EXTERNAL_STOREAGE_PERMISSION_CODE = 1;

    private Profile mProfile;
    private CircularNetworkImageView mImageViewProfile;
    private String mImagePath;

    private TextInputEditText mEditTextName, mEditTextUserName, mEditTextBio, mEditTextMobileNo, mEditTextEmail, mEditTextPassword, mEditTextCountry;
    private TextInputLayout mTextInputName, mTextInputUserName, mTextInputEmail, mTextInputMobileNo, mTextInputPassword;
    private Spinner mSpinnerGender;
    public static EditProfileFragment newInstance() {
        return newInstance(null);
    }

    public static EditProfileFragment newInstance(Profile profile) {
        EditProfileFragment fragment = new EditProfileFragment();
        fragment.mProfile = profile != null ? profile : new Profile();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        setHasOptionsMenu(true);
        setTitle(R.string.title_edit_profile);
        ((BaseActivity) getActivity()).showMainToolbar();

        mEditTextName = (TextInputEditText) view.findViewById(R.id.edittext_name);
        mEditTextName.addTextChangedListener(this);
        mTextInputName = (TextInputLayout) view.findViewById(R.id.textinput_name);

        mEditTextUserName = (TextInputEditText) view.findViewById(R.id.edittext_user_name);
        mEditTextUserName.addTextChangedListener(this);
        mTextInputUserName = (TextInputLayout) view.findViewById(R.id.textinput_user_name);

        mEditTextEmail = (TextInputEditText) view.findViewById(R.id.edittext_email);
        mEditTextEmail.addTextChangedListener(this);
        mTextInputEmail = (TextInputLayout) view.findViewById(R.id.textinput_email);

        mEditTextPassword = (TextInputEditText) view.findViewById(R.id.edittext_password);
        mEditTextPassword.addTextChangedListener(this);
        mTextInputPassword = (TextInputLayout) view.findViewById(R.id.textinput_password);

        mEditTextBio = (TextInputEditText) view.findViewById(R.id.edittext_bio);

        mSpinnerGender = (Spinner) view.findViewById(R.id.spinner_gender);

        mEditTextMobileNo = (TextInputEditText) view.findViewById(R.id.edittext_mobile_no);
        mEditTextMobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button buttonSave = (Button) view.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();

            }
        });

        mEditTextCountry = (TextInputEditText) view.findViewById(R.id.edittext_country);
        mEditTextCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mImageViewProfile = (CircularNetworkImageView) view.findViewById(R.id.imageview_profile_icon);
        mImageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(16)
            public void onClick(View view) {
                if (hasPermission(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE})) {
                    loadImageFromGallery();
                } else {
                    requestAppPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STOREAGE_PERMISSION_CODE);
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            if (isValid()) {
                pop();
            }
        }
        return true;
    }

    private void updateProfile() {
        mProfile.name = mEditTextName.getText().toString();
        mProfile.userName = mEditTextUserName.getText().toString();
        mProfile.bio = mEditTextBio.getText().toString();
        mProfile.email = mEditTextEmail.getText().toString();
        mProfile.mobileNo = mEditTextMobileNo.getText().toString();
        mProfile.password = mEditTextPassword.getText().toString();
        mProfile.gender = mSpinnerGender.getSelectedItemPosition();

        ProfileManager manager = new ProfileManager();
        manager.updateProfile(mProfile, new ProfileManager.OnUpdateProfileManager() {
            @Override
            public void onUpdateProfileSuccess(BaseResponse response) {
                Toast.makeText(getActivity(), response.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpdateProfileError(THException exception) {
                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setProfileDetails() {
        /*mEditTextName.setText(mProfile.firstName != null ? mProfile.firstName : "");
        mEditTextUserName.setText(mProfile.lastName != null ? mProfile.lastName : "");*/
        mEditTextMobileNo.setText(mProfile.mobileNo != null ? mProfile.mobileNo : "");
        mImageViewProfile.setDefaultImageResId(R.drawable.ic_default_avatar);
        mImageViewProfile.setErrorImageResId(R.drawable.ic_default_avatar);
        mImageViewProfile.setImageUrl(mProfile.profilePicUrl, THApplication.getInstance().getImageLoader());
    }

    private void loadImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_GALLERY_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_GALLERY_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            Bitmap bitmap = null;

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            mImagePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();
            mImageViewProfile.setImageBitmap(BitmapFactory.decodeFile(mImagePath));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isValid() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mEditTextName.getText().toString())) {
            mTextInputName.setError(getString(R.string.msg_error_required));
            isValid = false;
        } else if (TextUtils.isEmpty(mEditTextUserName.getText().toString())) {
            mTextInputUserName.setError(getString(R.string.msg_error_required));
            isValid = false;
        } else if (TextUtils.isEmpty(mEditTextEmail.getText().toString())) {
            mTextInputEmail.setError(getString(R.string.msg_error_required));
            isValid = false;
        } else if (TextUtils.isEmpty(mEditTextPassword.getText().toString())) {
            mTextInputPassword.setError(getString(R.string.msg_error_required));
            isValid = false;
        }
        return isValid;
    }

    @Override
    protected void onPermissionsGranted(int requestCode) {
        if (requestCode == PICK_GALLERY_IMAGE_REQUEST_CODE)
            loadImageFromGallery();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mTextInputName.setErrorEnabled(false);
        mTextInputUserName.setErrorEnabled(false);
        mTextInputEmail.setErrorEnabled(false);
        mTextInputPassword.setErrorEnabled(false);
    }
}
