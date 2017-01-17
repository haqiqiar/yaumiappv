package id.its.yaumirev_1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {
    SessionManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = new SessionManager();
        final String status=manager.getPreferences(SplashActivity.this,"status");
        Log.d("status",status);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (status.equals("1")){
                    Intent newIntent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(newIntent);
                }
                else {
                    Intent myIntent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(myIntent);

                }

                finish();
            }
        },3*1000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
