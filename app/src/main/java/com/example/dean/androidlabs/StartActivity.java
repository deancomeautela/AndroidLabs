package com.example.dean.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME,"In onCreate()");
        setContentView(R.layout.activity_start);
        final Button button = (Button) findViewById(R.id.im_a_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage =  new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(nextPage, 10);

            }
        });
        final Button chatButton = (Button) findViewById(R.id.chat_button);
        chatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            Intent nextPage = new Intent(StartActivity.this, ChatWindow.class);
                startActivityForResult(nextPage,0);
            }
        });
    }
    @Override
   protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }
    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if (requestCode == 10){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        if(responseCode==Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            CharSequence text = messagePassed;// "Switch is Off"
            int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
            Toast toast = Toast.makeText(getApplicationContext(),text,duration); //this is the ListActivity
            toast.show(); //display your message box

        }

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