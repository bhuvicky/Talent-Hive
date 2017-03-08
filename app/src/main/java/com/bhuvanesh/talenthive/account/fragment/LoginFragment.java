package com.bhuvanesh.talenthive.account.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.account.manager.AccountManager;
import com.bhuvanesh.talenthive.account.manager.SocialAuthManager;
import com.bhuvanesh.talenthive.account.model.LoginRequest;
import com.bhuvanesh.talenthive.account.model.LoginResponse;
import com.bhuvanesh.talenthive.activity.THActivity;
import com.bhuvanesh.talenthive.constant.IntentConstant;
import com.bhuvanesh.talenthive.dashboard.activity.DashboardActivity;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.model.Profile;
import com.bhuvanesh.talenthive.util.THLoggerUtil;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

public class LoginFragment extends BaseFragment {

    private String TAG = LoginFragment.class.getSimpleName();

    private TextInputLayout mTextInputUsername, mTextInputPassword, mTextInputOTP;
    private EditText mEditTextUsername, mEditTextPswd, mEditTextOTP;
    private CallbackManager mCallbackManager;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_useraccount_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mEditTextUsername = (EditText) view.findViewById(R.id.useraccountUserNameEditText);
        mEditTextPswd = (EditText) view.findViewById(R.id.useraccountPasswordEditText);
        mEditTextOTP = (EditText) view.findViewById(R.id.useraccountOTPEditText);
        mTextInputUsername = (TextInputLayout) view.findViewById(R.id.useraccountUserNameTextInputLayout);
        mTextInputPassword = (TextInputLayout) view.findViewById(R.id.useraccountPasswordTextInputLayout);
        mTextInputOTP = (TextInputLayout) view.findViewById(R.id.useraccountOTPTextInputLayout);
        Button loginButton = (Button) view.findViewById(R.id.useraccountLoginButton);
        TextView forgotTxtView = (TextView) view.findViewById(R.id.useraccountForgotTextView);


        LoginButton buttonFbLogin = (LoginButton) view.findViewById(R.id.button_fb_login);
        buttonFbLogin.setReadPermissions("email", "public_profile");
        buttonFbLogin.setFragment(this);
        mCallbackManager = CallbackManager.Factory.create();
        SocialAuthManager manager = new SocialAuthManager();
        manager.initFbLogin(buttonFbLogin, mCallbackManager, new SocialAuthManager.OnFbLoginManager() {
            @Override
            public void onFbLoginSuccess(Profile profile) {

            }

            @Override
            public void onFbLoginError(THException exception) {

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    showProgressDialog(R.string.progress_dialog_login);

                    LoginRequest request = new LoginRequest();
                    request.username = mEditTextUsername.getText().toString();
                    request.password = mEditTextPswd.getText().toString();

                    AccountManager manager = new AccountManager();
                    manager.doAuthenticate(request, new AccountManager.IUserLoginManager() {
                        @Override
                        public void OnUserLoginSuccess(LoginResponse response) {
                            if (isAdded()) {
                                dismissProgressDialog();
                                THLoggerUtil.debug("resp",response.roles);
                                Intent intent=new Intent(getActivity(), DashboardActivity.class);
                                intent.putExtra(IntentConstant.USERNAME,response.roles);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void OnUserLoginError(THException exception) {
                            if (isAdded()) {
                                dismissProgressDialog();
                                Toast.makeText(getContext(), "aa"+exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        forgotTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //TODO: Tobe removed
        mEditTextUsername.setText("karthikeyana97@gmail.com");
        mEditTextPswd.setText("2345");

        setHasOptionsMenu(true);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isValid() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mEditTextUsername.getText().toString())) {
            mTextInputUsername.setErrorEnabled(true);
            mTextInputUsername.setError(getResources().getString(R.string.msg_error_username));
            isValid = false;
        } else if (TextUtils.isEmpty(mEditTextPswd.getText().toString())) {
            mTextInputPassword.setErrorEnabled(true);
            mTextInputPassword.setError(getResources().getString(R.string.msg_error_password));
            isValid = false;
        }
        return isValid;
    }

}
