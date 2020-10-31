package com.example.oflinemaphelper.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oflinemaphelper.R;

public class DevSettingsActivity extends AppCompatActivity {

    private static final String TAG = "DevSettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_settings);
        try {
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
            }
        } catch (Exception exception) {
            Log.e(TAG, "onCreate: ", exception);
        }
        final Preference myPreference = new Preference(this);

        final EditText mobileTv = findViewById(R.id.server_mobile_no_tv);
        final EditText serverUrlTv = findViewById(R.id.server_url_tv);
        Button saveBtn = findViewById(R.id.save_settings);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPreference.setServerMobileNo(mobileTv.getText().toString());
                myPreference.setServerUrl(serverUrlTv.getText().toString());
                Toast.makeText(DevSettingsActivity.this, "Settings Saved", Toast.LENGTH_SHORT).show();
            }
        });

        mobileTv.setText(myPreference.getServerMobileNo());
        serverUrlTv.setText(myPreference.getServerUrl());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}