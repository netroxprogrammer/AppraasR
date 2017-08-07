package netroxtech.com.appraasr.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import netroxtech.com.appraasr.BuildConfig;
import netroxtech.com.appraasr.R;
import netroxtech.com.appraasr.Units.Constants;

public class EntryPoint extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {
    //Signin button
    private SignInButton signInButton;
    //Signing Options
    private GoogleSignInOptions gso;
    //google api client
    private GoogleApiClient mGoogleApiClient;
    //Signin constant to check the activity result
    private ProgressDialog mProgressDialog;           // Show ProgressBar
    private LoginButton loginButton;                //  Facebook Button
    private CallbackManager callbackManager;
    Button signUp,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_point);
         setXML();
        initializeGoogleClient();
        handleFBLoginButton();
        Profile p = Profile.getCurrentProfile();
     Toast.makeText(this,p.getFirstName(),Toast.LENGTH_LONG).show();
    }
    public void initializeGoogleClient(){
        //Initializing google signin option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton.setScopes(gso.getScopeArray());
    }
    private void signIn() {
        //Creating an intent
        showProgressDialog();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(Constants.TAG, "onConnectionFailed:" + connectionResult);
    }
    public  void setXML(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        signUp = (Button)findViewById(R.id.entryPoint_activity_signup_btn);
        login  = (Button)findViewById(R.id.entryPoint_activity_login_btn);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        // If using in a fragment
        signUp.setOnClickListener(this);
        login.setOnClickListener(this);
        //Setting onclick listener to signing button
        signInButton.setOnClickListener(this);
        loginButton.setReadPermissions("email", "public_profile", "user_friends");
    }
public  void handleFBLoginButton(){
    loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object,
                                                GraphResponse response) {

                            if (BuildConfig.DEBUG) {
                                FacebookSdk.setIsDebugEnabled(true);
                                FacebookSdk
                                        .addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
                                if (Profile.getCurrentProfile() != null) {
                                    Log.d(Constants.TAG + " Profile: ", "AccessToken.getCurrentAccessToken()"
                                            + AccessToken
                                            .getCurrentAccessToken()
                                            .toString());
                                    Profile.getCurrentProfile().getId();
                                    Profile.getCurrentProfile().getFirstName();
                                    Profile.getCurrentProfile().getLastName();
                                    Profile.getCurrentProfile().getProfilePictureUri(50, 50);
                                    //String email=UserManager.asMap().get(“email”).toString();
                                }
                            }
                        }
                    });
            request.executeAsync();
              }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "Please try Again" , Toast.LENGTH_LONG).show();

        }

        @Override
        public void onError(FacebookException e) {
            Log.e(Constants.TAG,e.toString());
        }
    });

}
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.sign_in_button){
            signIn();
        }
        if(view.getId()==R.id.entryPoint_activity_signup_btn){
            Intent  intent = new Intent(EntryPoint.this,SignUp.class);
            startActivity(intent);

        }
        if(view.getId()==R.id.entryPoint_activity_login_btn){
            Intent  intent = new Intent(EntryPoint.this,Login.class);
            startActivity(intent);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == Constants.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }


    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            hideProgressDialog();
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            Toast.makeText(this, acct.getEmail(), Toast.LENGTH_LONG).show();

        } else {
            //If login fails
            Toast.makeText(this, "Login Failed Please Try Again", Toast.LENGTH_LONG).show();
        }
    }
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Information Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

}
