package netroxtech.com.appraasr.applications;

import android.content.Context;
import android.content.SharedPreferences;

import netroxtech.com.appraasr.Units.Constants;

/**
 * Created by mac on 6/28/2017.
 */

public class SharePrefInit  {
    SharedPreferences mPref;
    Context mcontext;
    public SharePrefInit(Context context){
        mcontext = context;
        mPref = mcontext.getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE);
    }


    public void saveUserLogin(String userData){
        SharedPreferences.Editor editor  = mPref.edit();
        editor.putString(Constants.PREF_USER_DATA,userData);
        editor.commit();
    }
}
