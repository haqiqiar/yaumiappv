package id.its.yaumirev_1;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Zachary on 5/12/2016.
 */
public class SessionManager {
    public void setPreferences(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences("YaumiUser",Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getPreferences(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences("YaumiUser",Context.MODE_PRIVATE);
        String position = pref.getString(key, " ");
        return position;
    }
}
