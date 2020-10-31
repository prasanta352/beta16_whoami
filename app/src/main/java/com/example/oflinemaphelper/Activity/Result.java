package com.example.oflinemaphelper.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.oflinemaphelper.Activity.Implementation.BaseImplementation;
import com.example.oflinemaphelper.Activity.Implementation.SmsImplementation;
import com.example.oflinemaphelper.R;

import java.util.List;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        SmsImplementation abs = new SmsImplementation();
        abs.setLocation("from","to");
        abs.sendRequest();
        abs.startGettingResponse(new BaseImplementation.OnGettingResponseListener() {
            @Override
            public void onResponse(List<String> responses) {

            }
        });
    }
}