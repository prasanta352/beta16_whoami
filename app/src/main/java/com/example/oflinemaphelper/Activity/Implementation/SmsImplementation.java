package com.example.oflinemaphelper.Activity.Implementation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.List;

public class SmsImplementation  implements BaseImplementation {

    private SmsManager smsManager;
    private String mMessage = null;
    public String mFrom = null;
    public String mTo = null;

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

        if((mFrom != null) && (mTo != null)) {

            String message = mFrom +" "+ mTo;
            smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("9547466422", null, message, null, null);
            responseListener.onResponse(true);
        } else {
            responseListener.onResponse(false);
        }
    }

    @Override
    public void startGettingResponse(OnGettingResponseListener responseListener) {

        // 1. wait for response sms
        // 2. on getting response sms call responseListener.onResponse with the sms


        responseListener.onResponse(mMessage);

    }



}