package com.example.oflinemaphelper.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.oflinemaphelper.Activity.Implementation.BaseImplementation;
import com.example.oflinemaphelper.Activity.Implementation.SmsImplementation;
import com.example.oflinemaphelper.R;

import java.util.List;

public class Result extends AppCompatActivity {

    private static final String TAG = "Result";
    private int PERMISSIONS_REQUEST_CODE_ACCESS_SEND_RECEIVE = 1001;
    private String mFrom;
    private String mTo;
    private boolean mSentSMS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        // check if sms permission is available or not. It is necessary for sending and receiving sms
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS
            }, PERMISSIONS_REQUEST_CODE_ACCESS_SEND_RECEIVE);
        }


        //get the current intent and extract attached extras from the intent
        Intent intent = getIntent();
        mFrom = intent.getStringExtra("FROM");
        mTo = intent.getStringExtra("TO");
        Log.d(TAG, "onCreate: from -> " + mFrom + "  to -> " + mTo);




        SmsImplementation sms = new SmsImplementation();
        sms.setLocation(mFrom, mTo);
        sms.sendRequest(new BaseImplementation.OnSendRequestResponseListener() {
            @Override
            public void onResponse(boolean success) {

                mSentSMS = success;
            }
        });
        sms.startGettingResponse(new BaseImplementation.OnGettingResponseListener() {
            @Override
            public void onResponse(List<String> responses) {

            }
        });
    }
}