package com.example.android.data;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.outputText);
        output.setText(R.string.ready_to_code);
    }

    public void onCreateButtonClick(View view) {
    }

    public void onReadButtonClick(View view) {
    }

    public void onDeleteButtonClick(View view) {
    }
}
