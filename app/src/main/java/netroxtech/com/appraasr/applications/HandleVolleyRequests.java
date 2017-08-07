package netroxtech.com.appraasr.applications;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import netroxtech.com.appraasr.Models.CreateAccount;
import netroxtech.com.appraasr.Units.Constants;
import netroxtech.com.appraasr.activity.Login;
import netroxtech.com.appraasr.activity.Main2Activity;

/**
 * Created by mac on 6/24/2017.
 */

public class HandleVolleyRequests {
    String status;
    Context context;
    JSONObject reader;
    SharePrefInit prefInit;
    public  HandleVolleyRequests(Context  context){
        this.context  = context;
        prefInit= new SharePrefInit(context);
    }
    /**
     *      create User
     * @param account
     * @param coordinatorLayout
     * @return
     */
    public String createUser(final CreateAccount account, final CoordinatorLayout coordinatorLayout){
        final ProgressDialog pDialog = new ProgressDialog(this.context);
        pDialog.setMessage("Wait  few seconds ");
        pDialog.show();

        StringRequest sendResqurst = new StringRequest(Request.Method.POST, Constants.CREATE_USER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    reader = new JSONObject(response);
                    JSONObject data1 = reader.getJSONObject("result");
                    Log.v("data",data1.getString("success"));
                    String value = data1.getString("success");
                    if (value.equalsIgnoreCase("1")) {
                        pDialog.dismiss();
                        showMessage(coordinatorLayout,"Account Create Successfully  :) ","close");
                        goTologin();
                        }else{
                        status = value;
                        pDialog.dismiss();
                        showMessage(coordinatorLayout,"sorry Try Again :( ","Retry");

                    }
                 }

             catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error ",error.toString());
                pDialog.hide();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params =  new HashMap<>();
                params.put(Constants.firstName,account.getFirstName());
                params.put(Constants.lastName,account.getLastName());
                params.put(Constants.email,account.getEmail());
                params.put(Constants.userName,account.getUserName());
                params.put(Constants.password,account.getPassword());
                params.put(Constants.facebookApiKey,account.getFacebookApiKey());
                params.put(Constants.userType,account.getUserType());
                params.put(Constants.profileImage,account.getProfileImage());

                return params;
            }
        };
        VolleyInitializer.getmInstance(this.context).addToRequestQueue(sendResqurst);
        return status;
    }

    /**
     *    ShowMessage  on Snack Bar
     * @param coordinatorLayout
     * @param message
     * @param action
     */
    public void  showMessage(CoordinatorLayout coordinatorLayout, String message,String action){

        Snackbar snackbar =      Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_LONG)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    /**
     *  Go to  Login Activity
     */
    public void goTologin(){
        Intent intent = new Intent(context, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
    public void GoUserAccount(){
        Intent intent = new Intent(context, Main2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    /**
     *    Login  User Function
     * @param coordinatorLayout
     */

    public void  loginUser(final CreateAccount account, final CoordinatorLayout coordinatorLayout){
        final ProgressDialog pDialog = new ProgressDialog(this.context);
        pDialog.setMessage("Wait  few seconds ");
        pDialog.show();

        StringRequest sendResqurst = new StringRequest(Request.Method.POST, Constants.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    reader = new JSONObject(response);
                    if(reader.length() > 0) {
                        JSONObject data1 = reader.getJSONObject("user");
                        Log.v("datajson", data1.toString());
                        String value = data1.getString("success");
                        if (value.equalsIgnoreCase("1")) {
                            pDialog.dismiss();
                            prefInit.saveUserLogin(String.valueOf(data1));
                            showMessage(coordinatorLayout, "Login Successfully  :) ", "close");
                            GoUserAccount();
                        } else {

                            pDialog.dismiss();
                            showMessage(coordinatorLayout, "sorry Try Again :( ", "Retry");

                        }
                    }else{
                        showMessage(coordinatorLayout, "Sorry no Data Found :( ", "Retry");
                    }
                }

                catch(JSONException e){
                    e.printStackTrace();
                    pDialog.dismiss();
                    showMessage(coordinatorLayout,"sorry Try Again :( ","Retry");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error ",error.toString());
                pDialog.hide();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params =  new HashMap<>();
                params.put(Constants.email,account.getEmail());
                params.put(Constants.password,account.getPassword());


                return params;
            }
        };
        VolleyInitializer.getmInstance(this.context).addToRequestQueue(sendResqurst);

    }
}
