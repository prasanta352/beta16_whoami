package com.example.oflinemaphelper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oflinemaphelper.Activity.Implementation.BaseImplementation;
import com.example.oflinemaphelper.Activity.Implementation.SmsImplementation;
import com.example.oflinemaphelper.R;

import java.util.ArrayList;
import java.util.List;

public class Result extends AppCompatActivity {

    private static final String TAG = "Result";
    private ImageView smsSending;
    private TextView smsSendingText;
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
        smsSending = findViewById(R.id.sms_sending);
        smsSendingText = findViewById(R.id.sms_is_sending_text);
        mProgressBar.setVisibility(View.VISIBLE);


        //get the current intent and extract attached extras from the intent
        Intent intent = getIntent();
        mFrom = intent.getStringExtra("FROM");
        mTo = intent.getStringExtra("TO");
        Log.d(TAG, "onCreate: from -> " + mFrom + "  to -> " + mTo);


        attemptSendSms();

    }


    private void attemptSendSms() {
        final Preference preference = new Preference(this);
        final SmsImplementation sms = new SmsImplementation(this);

        final List<String> smsList = new ArrayList<>();
        //the array adapter to load data into list
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Result.this, android.R.layout.simple_list_item_1, smsList);
        //attaching adapter to listview
        mListView.setAdapter(arrayAdapter);

        sms.setLocation(mFrom, mTo);


        new MockSmsReceiver().sendSms(this, preference.getServerMobileNo(), sms.makeSmsBody(), new MockSmsReceiver.OnMessageSendReceiver() {
            @Override
            public void onSmsSent() {
                Log.d(TAG, "onSmsSent: Query Sms Send");

                smsSendingText.setText("Sms Has Been Sent Waiting For Response ..");
                sms.startGettingResponse(new BaseImplementation.OnGettingResponseListener() {
                    @Override
                    public void onResponse(String responses) {
                        Log.d(TAG, "onResponse: " + responses);
                        smsSending.setVisibility(View.GONE);
                        smsSendingText.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.GONE);
                        arrayAdapter.add(responses);
                    }
                });
            }
        });


    }


}