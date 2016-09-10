package com.bhuvanesh.talenthive.account.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.account.manager.AccManager;
import com.bhuvanesh.talenthive.account.model.LoginResponse;
import com.bhuvanesh.talenthive.constant.IntentConstant;
import com.bhuvanesh.talenthive.dashboard.activity.DashboardActivity;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.util.THLoggerUtil;

public class LoginFragment extends BaseFragment implements AccManager.IUserLoginManager {
    private String TAG = LoginFragment.class.getSimpleName();
    private TextInputLayout mUsernameTIL, mPasswordTIL, mOTPTIL;
    private EditText mUsernameEdtText, mPwdEdtText, mOTPEdtText;
    private String mUsername, mPwd;

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


        mUsernameEdtText = (EditText) view.findViewById(R.id.useraccountUserNameEditText);
        mPwdEdtText = (EditText) view.findViewById(R.id.useraccountPasswordEditText);
        mOTPEdtText = (EditText) view.findViewById(R.id.useraccountOTPEditText);
        mUsernameTIL = (TextInputLayout) view.findViewById(R.id.useraccountUserNameTextInputLayout);
        mPasswordTIL = (TextInputLayout) view.findViewById(R.id.useraccountPasswordTextInputLayout);
        mOTPTIL = (TextInputLayout) view.findViewById(R.id.useraccountOTPTextInputLayout);
        Button loginButton = (Button) view.findViewById(R.id.useraccountLoginButton);
        TextView forgotTxtView = (TextView) view.findViewById(R.id.useraccountForgotTextView);
        mUsernameEdtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mUsernameTIL.setErrorEnabled(false);
            }
        });
        mPwdEdtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPasswordTIL.setErrorEnabled(false);
            }
        });
        mOTPEdtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOTPTIL.setErrorEnabled(false);
            }
        });

        //Click Action on Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    showProgressDialog(R.string.progress_dialog_login);
                    AccManager accManager = new AccManager();
                    accManager.doAuthenticate(mUsername, mPwd, LoginFragment.this);
                }
            }
        });

        forgotTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //TODO: Tobe removed
        mUsernameEdtText.setText("karthikeyana97@gmail.com");
        mPwdEdtText.setText("2345");

        setHasOptionsMenu(true);

    }

    private boolean isValid() {
        mUsername = mUsernameEdtText.getText().toString();
        mPwd = mPwdEdtText.getText().toString();
        if (TextUtils.isEmpty(mUsername)) {
            mUsernameTIL.setErrorEnabled(true);
            mUsernameTIL.setError(getResources().getString(R.string.msg_error_username));
            return false;
        } else if (TextUtils.isEmpty(mPwd)) {
            mPasswordTIL.setErrorEnabled(true);
            mPasswordTIL.setError(getResources().getString(R.string.msg_error_password));
            return false;
        }
        return true;
    }

    @Override
    public void OnUserLoginSuccess(LoginResponse response) {
        if (isAdded()) {
            dismissProgressDialog();
            new THLoggerUtil().debug("resp",response.roles);
         //   getActivity().finish();
            Intent intent=new Intent(getActivity(), DashboardActivity.class);
           if(null!=response)
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
}
