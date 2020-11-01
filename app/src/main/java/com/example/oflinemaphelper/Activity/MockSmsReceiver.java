package com.example.oflinemaphelper.Activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import org.json.JSONArray;

import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MockSmsReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "MockSmsReceiver";

    public void sendSms(Context ctx, String to, String messageBody, final OnMessageSendReceiver onMessageSendReceiver) {
        SmsManager smsManager = SmsManager.getDefault();
        Log.d(TAG, String.format("sendSms: to=> %s messageBody %s", to, messageBody));
        final String SENT = "SMS_SENT";
        PendingIntent sentPI = PendingIntent.getBroadcast(ctx, 0, new Intent(SENT), 0);
        ;

        smsManager.sendTextMessage(to, null, messageBody, sentPI, null);

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(SENT)) {
                    onMessageSendReceiver.onSmsSent();
                }

            }
        };


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SENT);
        ctx.getApplicationContext().registerReceiver(br, intentFilter);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent);
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

                /*
                 * Message format
                 * -------------------------|
                 * FROM: {{FROM BODY}} \n   |
                 * TO: {{TO BODY}}          |
                 * -------------------------|
                 * Example                  |
                 * -------------------------|
FORM: Raghubir Singh Junior Modern School Dilhi INDIA
TO: Taj Mahal new Dilhi INDIA
                 */
                    String line1 = message.split("\n")[0];
                    String line2 = message.split("\n")[1];
                    String from = line1.replace("FROM: ", "");
                    String to = line2.replace("TO: ", "");

                    Log.d(TAG, String.format("onReceive: length -> %s from-> '%s' to -> '%s'", (message.length()), from, to));
                    Log.d(TAG, "onReceive: message ->" + message + " sender ->" + sender);
                    getDirection(context, sender, from, to);
                } else {
                    Log.d(TAG, "onReceive: skip");
                }


            }
        }

    }

    private void getDirection(final Context ctx, final String mobileNo, final String from, final String to) {
        final DirectionProvider dp = new DirectionProvider();

        executeLocalhostSmsForEmulator(ctx, mobileNo,from,to);
        dp.getGeoCode(from, new DirectionProvider.OnLocationWatcher() {
            @Override
            public void OnLocationGet(final DirectionProvider.Location fromLocation) {
                Log.d(TAG, String.format("OnLocationGet(fromLocation): longitude -> %s latitude -> %s", fromLocation.longitude, fromLocation.latitude));
                dp.getGeoCode(to, new DirectionProvider.OnLocationWatcher() {
                    @Override
                    public void OnLocationGet(final DirectionProvider.Location toLocation) {
                        Log.d(TAG, String.format("OnLocationGet(toLocation): longitude -> %s latitude -> %s", toLocation.longitude, toLocation.latitude));
                        dp.getDirection(toLocation, fromLocation, new DirectionProvider.OnDirectionWatcher() {
                            @Override
                            public void OnStepsGet(ArrayList<String> steps) {
                                for (int i = 0; i < steps.size(); i++) {
                                    final String step = steps.get(i);
                                    Log.d(TAG, "OnStepsGet: step ---- "+step);
                                    sendSms(ctx, mobileNo, String.format("%s. %s",i,step), new OnMessageSendReceiver() {
                                        @Override
                                        public void onSmsSent() {
                                            Log.d(TAG, String.format("onSmsSent: mobile ->%s step -> %s ", mobileNo, step));
                                        }
                                    });

                                }
                                Log.d(TAG, "OnStepsGet: " + steps);
                            }
                        });

                    }
                });
            }
        });

//
//        DirectionProvider.Location fromLocation = new DirectionProvider.Location(77.2274, 28.603701);
//        DirectionProvider.Location toLocation = new DirectionProvider.Location(77.223566, 28.604689);


    }

    private void executeLocalhostSmsForEmulator(Context ctx, final String mobileNo, final String from, final String to) {
        Log.d(TAG, "executeLocalhostSmsForEmulator: ");
        final Preference myPreference = new Preference(ctx);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String url = myPreference.getServerUrl() + "?from="+ from +"&to="+to+"&mobile="+mobileNo;
                    //Your code goes here
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Log.d(TAG, "url: "+url);
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

    interface OnMessageSendReceiver {
        void onSmsSent();
    }
}