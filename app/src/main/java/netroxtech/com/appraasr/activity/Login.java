package netroxtech.com.appraasr.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import netroxtech.com.appraasr.Models.CreateAccount;
import netroxtech.com.appraasr.R;
import netroxtech.com.appraasr.applications.HandleVolleyRequests;

public class Login extends AppCompatActivity  implements View.OnClickListener{

    HandleVolleyRequests requests;
    Context context;
    EditText email , password;
    TextView loginLabel;
    CoordinatorLayout coordinatorLayout;
    Button loginBtn;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context  =  this;
        setXML();
    }
    public void setXML() {
        view = this.getCurrentFocus();

        requests = new HandleVolleyRequests(context);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue.ttf");
        loginLabel = (TextView) findViewById(R.id.loginActivity_text_label);
        email = (EditText) findViewById(R.id.login_activity_email);
        password = (EditText) findViewById(R.id.login_activity_password);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_sign_in_layout);
        loginBtn = (Button)findViewById(R.id.login_activity_login_btn);
        email.setTypeface(font);
        password.setTypeface(font);
        loginLabel.setTypeface(font);
        loginBtn.setOnClickListener(this);
    }
    public boolean checkEmptyFields(){
        if (email.getText().toString().equalsIgnoreCase("") ||
                password.getText().toString().equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }
    public void loginUsers(){
        if(checkEmptyFields()) {
            CreateAccount   account = new CreateAccount();
            account.setEmail(email.getText().toString());
            account.setPassword(password.getText().toString());
            requests.loginUser(account,coordinatorLayout);
            hideSoftKeyboard();
        }
        else{
            requests.showMessage(coordinatorLayout,"Email or Password incorrect","Retry");
        }
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.login_activity_login_btn){
            loginUsers();
        }
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}


