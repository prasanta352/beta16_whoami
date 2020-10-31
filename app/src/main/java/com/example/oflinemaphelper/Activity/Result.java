package com.example.oflinemaphelper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oflinemaphelper.Activity.Implementation.BaseImplementation;
import com.example.oflinemaphelper.Activity.Implementation.SmsImplementation;
import com.example.oflinemaphelper.R;

import java.util.ArrayList;
import java.util.List;

public class Result extends AppCompatActivity {

    private static final String TAG = "Result";
    private String mFrom;
    private String mTo;
    private ProgressBar mProgressBar;
    private ListView mListView;
    private boolean mSentSMS = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mProgressBar = findViewById(R.id.progress_circular);
        mListView = findViewById(R.id.listView);
        mProgressBar.setVisibility(View.VISIBLE);


        //get the current intent and extract attached extras from the intent
        Intent intent = getIntent();
        mFrom = intent.getStringExtra("FROM");
        mTo = intent.getStringExtra("TO");
        Log.d(TAG, "onCreate: from -> " + mFrom + "  to -> " + mTo);


        attemptSendSms();

    }


    private void attemptSendSms() {

        final List<String> smsList = new ArrayList<>();
        //the array adapter to load data into list
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Result.this, android.R.layout.simple_list_item_1, smsList);
        //attaching adapter to listview
        mListView.setAdapter(arrayAdapter);

        SmsImplementation sms = new SmsImplementation(this);
        sms.setLocation(mFrom, mTo);
        sms.sendRequest(new BaseImplementation.OnSendRequestResponseListener() {
            @Override
            public void onResponse(boolean success) {
                mSentSMS = success;
                Log.d(TAG, "onResponse: " + mSentSMS);
            }
        });

        if (mSentSMS) {
            sms.startGettingResponse(new BaseImplementation.OnGettingResponseListener() {
                @Override
                public void onResponse(String responses) {
                    Log.d(TAG, "onResponse: " + responses);
                    mProgressBar.setVisibility(View.GONE);
                    arrayAdapter.add(responses);
                }
            });

        }

    }


}