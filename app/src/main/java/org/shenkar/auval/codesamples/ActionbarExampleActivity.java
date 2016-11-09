package org.shenkar.auval.codesamples;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.transition.ChangeBounds;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * References:
 * https://developer.android.com/training/appbar/index.html
 * https://guides.codepath.com/android/Using-the-App-ToolBar
 *
 * @author amir uval
 */
public class ActionbarExampleActivity extends AppCompatActivity {

    int size = 0;

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

    /**
     * messing around with the squares in the middle of the view
     *
     * @param view the FAB when pressed
     */
    public void doAction(View view) {
        View blue = findViewById(R.id.blue_example);
        if (size == 0) {
            size = (int) getResources().getDimension(R.dimen.squares_size);
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) blue.getLayoutParams();

        if (Math.random() < 0.5) {
            layoutParams.bottomToTop = R.id.pink_example;
            layoutParams.topToBottom = -1;
        } else {
            layoutParams.bottomToTop = -1;
            layoutParams.topToBottom = R.id.pink_example;
        }

        if (Math.random() < 0.5) {
            layoutParams.leftToRight = -1;
            layoutParams.rightToLeft = R.id.pink_example;
        } else {
            layoutParams.leftToRight = R.id.pink_example;
            layoutParams.rightToLeft = -1;
        }

        blue.setLayoutParams(layoutParams);

        // animating layout change!
        Transition transition = new ChangeBounds();
        transition.setDuration(1000);

        // options: FastOutLinearInInterpolator, AccelerateInterpolator, LinearOutSlowInInterpolator
        transition.setInterpolator(new FastOutSlowInInterpolator());
        transition.setStartDelay(200);
        TransitionManager.beginDelayedTransition(((ViewGroup) blue.getParent()), transition);


    }
}
