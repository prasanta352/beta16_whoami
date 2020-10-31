package com.example.oflinemaphelper.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oflinemaphelper.Activity.Implementation.BaseImplementation;
import com.example.oflinemaphelper.Activity.Implementation.SmsImplementation;
import com.example.oflinemaphelper.R;

import java.util.List;

public class Result extends AppCompatActivity {

    private static final String TAG = "Result";
    private String mFrom;
    private String mTo;
    private ProgressBar mProgressBar;
    private boolean mSentSMS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mProgressBar = findViewById(R.id.progress_circular);
        mProgressBar.setVisibility(View.VISIBLE);

        //get the current intent and extract attached extras from the intent
        Intent intent = getIntent();
        mFrom = intent.getStringExtra("FROM");
        mTo = intent.getStringExtra("TO");
        Log.d(TAG, "onCreate: from -> " + mFrom + "  to -> " + mTo);



        attemptSendSms();

    }



    private void attemptSendSms() {

        SmsImplementation sms = new SmsImplementation();
        sms.setLocation(mFrom, mTo);
        sms.sendRequest(new BaseImplementation.OnSendRequestResponseListener() {
            @Override
            public void onResponse(boolean success) {

                mSentSMS = success;
                Log.d(TAG, "onResponse: " + mSentSMS);
            }
        });
//        sms.startGettingResponse(new BaseImplementation.OnGettingResponseListener() {
//            @Override
//            public void onResponse(String responses) {
//
//                Log.d(TAG, "onResponse: " + responses);
//                Toast.makeText(Result.this, responses, Toast.LENGTH_LONG).show();
//            }
//        });

        if(mSentSMS) {
            receiveSms();
        }

    }


    private void receiveSms() {

        BroadcastReceiver smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get the data (SMS data) bound to intent
                Bundle bundle = intent.getExtras();

                if (bundle != null) {
                    // Retrieve the SMS Messages received

                    Object[] sms = (Object[]) bundle.get("pdus");

                    // For every SMS message received
                    for (int i=0; i < sms.length; i++) {
                        // Convert Object array
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                        String phone = smsMessage.getOriginatingAddress();
                        String message = smsMessage.getMessageBody().toString();

                        String mMessage = phone + ": " + message;
                        Toast.makeText(Result.this, mMessage, Toast.LENGTH_LONG).show();


                    }
                }


            }
        };

    }
}