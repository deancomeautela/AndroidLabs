package com.example.dean.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button button = (Button) findViewById(R.id.button_id);
        final SharedPreferences prefs = getSharedPreferences("emailPref", Context.MODE_PRIVATE);

        final SharedPreferences.Editor writer = prefs.edit();
        final EditText email = (EditText) findViewById(R.id.emailField);
         email.setText(prefs.getString("DefaultEmail","email@domain.com"));
        //writer.putString("Email" ,findViewById(R.id.emailField))
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writer.putString("DefaultEmail",email.getText().toString());
                writer.commit();
                Intent nextPage = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(nextPage);


            }

            ;
            });
    }
    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if (requestCode == 10){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
    }
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }


    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");

    }

    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");

    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");

    }
}
