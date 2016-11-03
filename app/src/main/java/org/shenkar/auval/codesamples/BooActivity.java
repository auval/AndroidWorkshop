package org.shenkar.auval.codesamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BooActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boo);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            TextView booView = (TextView) findViewById(R.id.boo);
            booView.setText(name + "\nBoo!");

        }

    }
}
