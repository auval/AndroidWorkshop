package org.shenkar.auval.codesamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * References:
 * https://developer.android.com/training/appbar/index.html
 * https://guides.codepath.com/android/Using-the-App-ToolBar
 *
 * @author amir uval
 */
public class ActionbarExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionbar_example);

        // Toolbar that will act as ActionBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // myToolbar is not *the* ActionBar. We need to access ActionBar via getSupportActionBar()
        getSupportActionBar().setTitle("I'm an App Toolbar");

        // setDisplayHomeAsUpEnabled() displays a left button.
        // It uses android:parentActivityName="..." from the manifest
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lesson3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_toast:
                Toast.makeText(this, "Cheers!", Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void doHideBar(View view) {
        // will animate if this flag is defined at the parent:    android:animateLayoutChanges="true"
        getSupportActionBar().hide();
    }

    public void doShowBar(View view) {
        // will animate if this flag is defined at the parent:    android:animateLayoutChanges="true"
        getSupportActionBar().show();
    }
}
