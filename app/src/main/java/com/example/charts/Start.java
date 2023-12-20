package com.example.charts;

import static com.example.charts.activities.Login.userininlogin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Toast;

import com.example.charts.R;
import com.example.charts.activities.Home;
import com.example.charts.activities.SingnORLogin;

public class Start extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String shahardprefrences_name="inlogin";
    public static final String key_ofvalue="inlogin";
    public static final String keyRememberMe = "remember_me";
    public static final String keyUserEmail = "user_email";
    public static final String keyUserPassword = "user_password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the title or label from the action bar
        setContentView(R.layout.activity_wellcome);
        sharedPreferences=getSharedPreferences(shahardprefrences_name,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        SharedPreferences sharedPreferences=getSharedPreferences(shahardprefrences_name,0);
        String loggedInUserEmail = sharedPreferences.getString(userininlogin,"");

        Toast.makeText(this, loggedInUserEmail, Toast.LENGTH_SHORT).show();

        // Delay for 30 seconds (10 * 1000 milliseconds)
        int delayMillis = 10 * 100;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent to start the second activity
                boolean islogin=sharedPreferences.getBoolean(key_ofvalue,false);
//                Intent intent = new Intent(getApplicationContext(), SingnORLogin.class);
//                startActivity(intent);
            if(islogin){
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();

            }
            else {
                Intent intent = new Intent(getApplicationContext(), SingnORLogin.class);
                startActivity(intent);
                // Finish the current activity (optional)
                finish();}
            }
        }, delayMillis);
    }
}