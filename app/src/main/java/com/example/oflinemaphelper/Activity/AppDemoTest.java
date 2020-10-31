package com.example.oflinemaphelper.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import org.json.JSONArray;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AppDemoTest extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "AppDemoTest";


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action != null && action.equals(SMS_RECEIVED)) {

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");

                assert pdus != null;
                if (pdus.length == 0) {
                    return;
                }
                // large message might be broken into many
                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody());
                }

                String sender = messages[0].getOriginatingAddress();
                String message = sb.toString();

                if (message.startsWith("FROM")) {
                    Log.d(TAG, "onReceive: message ->" + message + " sender ->" + sender);
                    getDirection(context, sender);
                } else {
                    Log.d(TAG, "onReceive: skip");
                }


            }
        }

    }

    private void getDirection(Context ctx, String mobileNo) {
        final Preference myPreference = new Preference(ctx);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //Your code goes here
                    Request request = new Request.Builder()
                            .url(myPreference.getServerUrl() + "?from=Taj+Mahal+new+Dilhi+INDIA&to=Raghubir+Singh+Junior+Modern+School+Dilhi+INDIA")
                            .build();

                    try {
                        OkHttpClient client = new OkHttpClient();

                        Response response = client.newCall(request).execute();

                        try {
                            String s = response.body().string();
                            JSONArray jsonArray = new JSONArray(s);

                            Log.d(TAG, "onCreate: " + s + " " + jsonArray.length());
                        } catch (NullPointerException ex) {
                            Log.e(TAG, "run: ", ex);
                        }


                    } catch (Exception ex) {
                        Log.e(TAG, "onCreate: ", ex);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}