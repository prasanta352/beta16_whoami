package com.example.oflinemaphelper.Activity.Implementation;

import java.util.List;

public class SmsImplementation implements BaseImplementation {

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

        responseListener.onResponse(false);
    }

    @Override
    public void startGettingResponse(OnGettingResponseListener responseListener) {

        // 1. wait for response sms
        // 2. on getting response sms call responseListener.onResponse with the sms

        List<String> list = null;
        responseListener.onResponse(list);
    }
}