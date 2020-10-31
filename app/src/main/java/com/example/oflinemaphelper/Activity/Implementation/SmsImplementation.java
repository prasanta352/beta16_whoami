package com.example.oflinemaphelper.Activity.Implementation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsImplementation implements BaseImplementation {

    public String mFrom = null;
    public String mTo = null;
    private final Context mContext;
    private final String TAG = "BaseImplementation";
    private final String DESTINATION_MOBILE_NO = "9547466422";

    public SmsImplementation(Context c) {
        super();
        mContext = c;
    }

    @Override
    public void setLocation(String from, String to) {
        this.mFrom = from;
        this.mTo = to;
    }

    @Override
    public void sendRequest(OnSendRequestResponseListener responseListener) {
        // 1. check if mFrom and mTo is not null
        // 2. send sms
        // 3. return sms sending status true|false

        if ((mFrom != null) && (mTo != null)) {

            String message = mFrom + " " + mTo;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(DESTINATION_MOBILE_NO, null, message, null, null);
            responseListener.onResponse(true);
        } else {
            responseListener.onResponse(false);
        }
    }

    @Override
    public void startGettingResponse(final OnGettingResponseListener responseListener) {

        // 1. wait for response sms
        // 2. on getting response sms call responseListener.onResponse with the sms

        BroadcastReceiver smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get the data (SMS data) bound to intent
                Bundle bundle = intent.getExtras();

                if (bundle != null) {
                    // Retrieve the SMS Messages received

                    Object[] sms = (Object[]) bundle.get("pdus");

                    // For every SMS message received
                    for (Object sm : sms) {
                        // Convert Object array
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sm);

                        String phone = smsMessage.getOriginatingAddress();
                        String message = smsMessage.getMessageBody();

                        String mMessage = phone + ": " + message;
                        Log.d(TAG, "onReceive: " + mMessage);

                        // message has been received call the call back function with the message
                        responseListener.onResponse(mMessage);
                    }
                }


            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        mContext.registerReceiver(smsReceiver, intentFilter);
    }


}