package org.shenkar.auval.codesamples;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mta.sharedutils.AsyncHandler;
import com.mta.sharedutils.UiHandler;

import org.shenkar.auval.db.DbHelper;

import java.util.ArrayList;


public class SqlExample extends AppCompatActivity {
    EditText mNameEdit = null;
    ArrayList<String> mNames = null;
    TextView mResultsTv;
    Spinner mSpinner = null;
    Runnable mSaver = new Runnable() {
        @Override
        public void run() {
            DbHelper.getDb(SqlExample.this).addRow(mNameEdit.getText().toString(), mSpinner.getSelectedItem().toString());
            mLoader.run(); // still user thread!
        }
    };
    Runnable mUiSetter = new Runnable() {
        @Override
        public void run() {
            if (mNames == null || mNames.isEmpty()) {
                mResultsTv.setText("No results yet for " + mSpinner.getSelectedItem().toString() +
                        ".\nPlease save something or select a different color!");
                return;
            }
            mResultsTv.setText("results:\n" + mNames.toString());
        }
    };
    Runnable mLoader = new Runnable() {
        @Override
        public void run() {
            mNames = DbHelper.getDb(SqlExample.this).whoLikesWhichColor(mSpinner.getSelectedItem().toString());
            UiHandler.post(mUiSetter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mNameEdit = (EditText) findViewById(R.id.edit_name);
        mResultsTv = (TextView) findViewById(R.id.results_pane);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSpinner = (Spinner) findViewById(R.id.spinner_colors);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

//                String color = adapterView.getItemAtPosition(position).toString();
                AsyncHandler.post(mLoader);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncHandler.post(mSaver);
            }
        });
    }

}
