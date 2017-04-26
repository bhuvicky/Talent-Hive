package com.bhuvanesh.talenthive.account.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
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
import com.bhuvanesh.talenthive.profile.fragment.EditProfileFragment;
import com.bhuvanesh.talenthive.util.THLoggerUtil;
import com.bhuvanesh.talenthive.util.THPreference;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.api.services.people.v1.PeopleScopes;

import org.json.JSONObject;

public class LoginFragment extends BaseFragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private String TAG = LoginFragment.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_SIGN_IN_REQUEST_CODE = 1;

    private TextInputLayout mTextInputUsername, mTextInputPassword, mTextInputOTP;
    private TextInputEditText mEditTextUsername, mEditTextPswd, mEditTextOTP;
    private CallbackManager mCallbackManager;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_useraccount_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mEditTextUsername = (TextInputEditText) view.findViewById(R.id.edittext_user_name);
        mEditTextPswd = (TextInputEditText) view.findViewById(R.id.edittext_password);
        mEditTextOTP = (TextInputEditText) view.findViewById(R.id.useraccountOTPEditText);
        mTextInputUsername = (TextInputLayout) view.findViewById(R.id.textinput_user_name);
        mTextInputPassword = (TextInputLayout) view.findViewById(R.id.textinput_password);
        mTextInputOTP = (TextInputLayout) view.findViewById(R.id.useraccountOTPTextInputLayout);
        Button loginButton = (Button) view.findViewById(R.id.button_login);
        TextView textViewForgetPswd = (TextView) view.findViewById(R.id.textview_forget_pswd);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(PeopleScopes.CONTACTS_READONLY))
                .requestServerAuthCode(getString(R.string.oauth2_0_web_client_google_people_api), false)
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) view.findViewById(R.id.button_google_sign_in);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText(getString(R.string.lbl_google_sign_in));

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
            }
        });


        LoginButton buttonFbLogin = (LoginButton) view.findViewById(R.id.button_fb_login);
        buttonFbLogin.setReadPermissions("email", "public_profile");
        buttonFbLogin.setFragment(this);
        mCallbackManager = CallbackManager.Factory.create();
        SocialAuthManager manager = new SocialAuthManager();
        manager.initFbLogin(buttonFbLogin, mCallbackManager, new SocialAuthManager.OnFbLoginManager() {
            @Override
            public void onFbLoginSuccess(Profile profile) {
                THPreference.getInstance().setFBLogin(true);
                startActivity(new Intent(getActivity(), DashboardActivity.class));
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

        textViewForgetPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //TODO: Tobe removed
        mEditTextUsername.setText("karthikeyana97@gmail.com");
        mEditTextPswd.setText("2345");

        // Terms & Conditions
        String stringTermsConditions = getString(R.string.lbl_sign_in_terms_conditions);
        SpannableString ss = new SpannableString(stringTermsConditions);
        ClickableSpan spanTermsAndCond = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan spanPrivacyPolicy = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(spanTermsAndCond, 38, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(spanPrivacyPolicy, 79, stringTermsConditions.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView termsConditions = (TextView) view.findViewById(R.id.textview_terms_conditions);
        termsConditions.setText(ss);
        termsConditions.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textViewSignUp = (TextView) view.findViewById(R.id.textview_sign_up);
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(R.id.layout_container, EditProfileFragment.newInstance());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        }
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            THPreference.getInstance().setGoogleLogin(true);
            THPreference.getInstance().setGoogleServerAuthCode(acct.getServerAuthCode());
            startActivity(new Intent(getActivity(), DashboardActivity.class));

        }
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
