package netroxtech.com.appraasr.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import netroxtech.com.appraasr.Models.CreateAccount;
import netroxtech.com.appraasr.R;
import netroxtech.com.appraasr.Units.Constants;
import netroxtech.com.appraasr.applications.HandleVolleyRequests;

public class SignUp extends AppCompatActivity  implements View.OnClickListener   {

    EditText firstName,lastName,email,password,userName;
    TextView signUpLabel;
    Button signBtn;
    Context context;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context  =  SignUp.this;
        setXML();

    }
    public void setXML(){

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/HelveticaNeue.ttf");
        signUpLabel = (TextView)findViewById(R.id.signupActivity_text_label);
        firstName   = (EditText)findViewById(R.id.signUp_activity_firstName);
        lastName    = (EditText) findViewById(R.id.signUp_activity_lastName);
        email       = (EditText)findViewById(R.id.signUp_activity_email);
        password    = (EditText)findViewById(R.id.signUp_activity_password);
        userName    = (EditText)findViewById(R.id.signUp_activity_userName);
        signBtn     = (Button)findViewById(R.id.signup__activity_signup_btn);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.activity_sign_up_layout);
        signUpLabel.setTypeface(font);
        firstName.setTypeface(font);
        lastName.setTypeface(font);
        email.setTypeface(font);
        password.setTypeface(font);
        userName.setTypeface(font);
        signBtn.setOnClickListener(this);

    }
    public boolean checkEmptyFields(){
        if (firstName.getText().toString().equalsIgnoreCase("") ||
                lastName.getText().toString().equalsIgnoreCase("") ||
                email.getText().toString().equalsIgnoreCase("") ||
                userName.getText().toString().equalsIgnoreCase("") ||
                password.getText().toString().equalsIgnoreCase("")) {

        return false;
        }

        return true;
    }
    public void registerUser(){
        if(checkEmptyFields()){
            CreateAccount account = new CreateAccount();
            account.setFirstName(firstName.getText().toString());
            account.setLastName(lastName.getText().toString());
            account.setEmail(email.getText().toString());
            account.setUserName(userName.getText().toString());
            account.setPassword(password.getText().toString());
            account.setFacebookApiKey("no");
            account.setUserType(Constants.USER_TYPE);
            account.setProfileImage("");
            String check = new HandleVolleyRequests(context).createUser(account,coordinatorLayout);
            Toast.makeText(this,check,Toast.LENGTH_LONG).show();
        }
        else
        {
            //Toast.
            Snackbar snackbar =      Snackbar.make(coordinatorLayout,"Please Fill All information",Snackbar.LENGTH_LONG)
                     .setAction("Retry", new View.OnClickListener() {
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
    }
    @Override
    public void onClick(View view) {
        if(view.getId() ==  R.id.signup__activity_signup_btn){
            registerUser();
        }
    }
}
