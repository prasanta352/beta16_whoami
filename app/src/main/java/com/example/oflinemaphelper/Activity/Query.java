package com.example.oflinemaphelper.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.oflinemaphelper.R;

public class Query extends AppCompatActivity {
    private static final String TAG = "Query";
    private EditText mFrom;
    private EditText mTo;
    private Button mQueryBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        mFrom = findViewById(R.id.from);
        mTo = findViewById(R.id.to);
        mQueryBTN = findViewById(R.id.queryBTN);

        //set a button click listener on mQueryButton
        mQueryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptQuery();
            }
        });
    }

    private void attemptQuery() {

        boolean cancel = false;
        View focusView = null;

        mFrom.setError(null);
        mTo.setError(null);

        String from = mFrom.getText().toString();
        String to = mTo.getText().toString();

        //checking whether from or to edit text empty
        if (TextUtils.isEmpty(from)) {
            mFrom.setError("This field is required.");
            focusView = mFrom;
            cancel = true;
        }
        if (TextUtils.isEmpty(to)) {
            mTo.setError("This field is required.");
            focusView = mTo;
            cancel = true;
        }

        if (cancel) {
            // There was an error; the error happened because either from or to
            // field or both was empty
            focusView.requestFocus();
        } else {
            Log.d(TAG, "attemptQuery: attempt");

        }

    }
}