package com.example.oflinemaphelper.Activity;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    private final static String MAIN_KEY = "MAIN_KEY";
    private final static String SERVER_IP_KEY = "server_ip";
    private final static String SERVER_MOBILE_NO_KEY = "server_mobile_no";
    private final SharedPreferences mySharedPref;
    SharedPreferences.Editor editor;

    public Preference(Context ctx) {
        mySharedPref = ctx.getSharedPreferences(MAIN_KEY, Context.MODE_PRIVATE);
        editor = mySharedPref.edit();
    }

    public String getServerUrl() {
        return mySharedPref.getString(SERVER_IP_KEY, null);
    }

    public boolean setServerUrl(String serverIp) {
        editor.putString(SERVER_IP_KEY, serverIp);
        return editor.commit();
    }

    public String getServerMobileNo() {
        return mySharedPref.getString(SERVER_MOBILE_NO_KEY, "9474433416");
    }

    public boolean setServerMobileNo(String serverMobileNo) {
        editor.putString(SERVER_MOBILE_NO_KEY, serverMobileNo);
        return editor.commit();
    }
}
