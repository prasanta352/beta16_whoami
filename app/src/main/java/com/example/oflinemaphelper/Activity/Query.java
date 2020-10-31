package com.example.oflinemaphelper.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.oflinemaphelper.R;

public class Query extends AppCompatActivity {
    private static final String TAG = "Query";
    private int PERMISSIONS_REQUEST_CODE_ACCESS_SEND_RECEIVE = 1001;
    private EditText mFrom;
    private EditText mTo;
    private Button mQueryBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        mFrom = findViewById(R.id.from);
        mTo = findViewById(R.id.to);
        mQueryBTN = findViewById(R.id.queryBTN);

        // check if sms permission is available or not. It is necessary for sending and receiving sms
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS
            }, PERMISSIONS_REQUEST_CODE_ACCESS_SEND_RECEIVE);
        }

        //set a button click listener on mQueryButton
        mQueryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptQuery();
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