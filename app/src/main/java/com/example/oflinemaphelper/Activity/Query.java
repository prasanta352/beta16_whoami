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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.oflinemaphelper.R;

public class Query extends AppCompatActivity {
    private static final String TAG = "Query";
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_SEND_RECEIVE = 1001;
    private static final String[] mStateArray = {"Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh", "Dadra and Nagar Haveli and Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Ladakh", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
    private EditText mFrom;
    private EditText mTo;
    private Spinner mStateSpinner;
    private String mState;

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
        mStateSpinner = findViewById(R.id.spinner_state);

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

        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(Query.this, android.R.layout.simple_spinner_item, mStateArray);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStateSpinner.setAdapter(adapterState);

        mStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mState = mStateSpinner.getSelectedItem().toString();
                Log.d(TAG, "onItemSelected: " + mState);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                intent.putExtra("FROM", from + ", " + mState + ", India");
                intent.putExtra("TO", to + ", " + mState + ", India");
                //starting the activity
                startActivity(intent);

//            finish();

            }
        }

    }
}