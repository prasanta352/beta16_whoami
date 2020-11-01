package com.example.oflinemaphelper.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.oflinemaphelper.R;

public class Query extends AppCompatActivity {
    private static final String TAG = "Query";
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_SEND_RECEIVE = 1001;
    private EditText mFrom;
    private EditText mTo;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.dev_settings) {
            Intent intent = new Intent(this, DevSettingsActivity.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        mFrom = findViewById(R.id.from);
        mTo = findViewById(R.id.to);
        Button mQueryBTN = findViewById(R.id.queryBTN);


        //set a button click listener on mQueryButton
        mQueryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptQuery();
            }
        });

//        Preference myPreference = new Preference(this);
//        Boolean s1 = myPreference.setServerUrl("http://192.168.0.108:3000");
//        Boolean s2 = myPreference.setServerMobileNo("8768104448");
////
        Log.d(TAG, "onCreate: ");



    }


    /*
     *  This function will be called when the done button is pressed.
     * */
    private void attemptQuery() {

        boolean cancel = false;
        View focusView = null;

        mFrom.setError(null);
        mTo.setError(null);

        String from = mFrom.getText().toString();
        String to = mTo.getText().toString();

        //checking whether from or to edit text empty
        if (TextUtils.isEmpty(from)) {
            mFrom.setError("This field is required.");
            focusView = mFrom;
            cancel = true;
        }
        if (TextUtils.isEmpty(to)) {
            mTo.setError("This field is required.");
            focusView = mTo;
            cancel = true;
        }

        if (cancel) {
            // There was an error; the error happened because either from or to
            // field or both was empty
            focusView.requestFocus();
        } else {
            Log.d(TAG, "attemptQuery: attempt");

            // check if sms permission is available or not. It is necessary for sending and receiving sms
            if (ActivityCompat.checkSelfPermission(Query.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(Query.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Query.this, new String[]{
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.RECEIVE_SMS
                }, PERMISSIONS_REQUEST_CODE_ACCESS_SEND_RECEIVE);
            } else {
                // permission was given.
                //creating and initializing an Intent object
                Intent intent = new Intent(this, Result.class);
                //attach the key value pair using putExtra to this intent
                intent.putExtra("FROM", from);
                intent.putExtra("TO", to);
                //starting the activity
                startActivity(intent);

//            finish();

            }
        }

    }
}