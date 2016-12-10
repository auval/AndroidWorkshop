package org.shenkar.auval.codesamples;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mta.sharedutils.AsyncHandler;
import com.mta.sharedutils.UiHandler;

import org.shenkar.auval.db.DbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * notice there's no View or Context referenced from ArrayItem
     * so it's ok to keep a static reference to it.
     * I need it static because I keep changes in memory and screen rotate would create a fresh
     * new arrayList otherwise.
     */
    final static ArrayList<ArrayItem> mValues = new ArrayList<>();

    /**
     * this is how to init static variables.
     * It will run once and only once, unless the app is killed and restart from scratch.
     */
    static {
        // define data for the list
        mValues.add(new ArrayItem("Lecture 2: Activities communicate", Lesson2Activity.class));
        mValues.add(new ArrayItem("Lecture 3: App Toolbar", ActionbarExampleActivity.class));
        mValues.add(new ArrayItem("Lecture 4: SQlite", SqlExample.class));

        /*
         This sample is actually the default activity you can get when you press
         on new > Activity > Settings Activity.
         But it gives some useful examples on preferences widgets.
         The basic settings app is in a different project:
         https://github.com/auval/MinimalSettingsActivity
        */
        mValues.add(new ArrayItem("Lecture 5: Settings Sample", SettingsActivity.class));
        mValues.add(new ArrayItem(".. Run the basic Settings app (if it's installed)",
                "com.mindtheapps.sampleprefs"));
        mValues.add(new ArrayItem("Lecture 6: Scene Transition", SceneTransitionActivity.class));
        mValues.add(new ArrayItem("Lecture 7a: Low Lever Graphics", LowLeverGraphicsActivity
                .class));
        mValues.add(new ArrayItem("Lecture 7b: Low Level Sprite Animation",
                SpriteAnimationActivity.class));
        mValues.add(new ArrayItem("Lecture 7c: Animation List (with SVG)", SvgViewsActivity
                .class));
        mValues.add(new ArrayItem("Lecture 7d: SVG on Bitmap background", BmpAndSvgActivity.class));

    }

    /**
     * replacing the ListView with this
     */
    RecyclerView mainList;
    /**
     * it supplies the view with the data in a very efficient way
     */
    private RecyclerView.Adapter mAdapter;

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ArrayItem clicked = (ArrayItem) view.getTag();
            if (clicked.getActivity() == null) {
                if (clicked.getPackageName() != null) {
                    // we need to launch a different app here, if it's available.
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(clicked
                            .getPackageName());
                    if (launchIntent == null) {
                        Toast.makeText(getBaseContext(), "Couldn't find activity for package:" +
                                clicked.getActivity(), Toast.LENGTH_LONG).show();

                        // example for removing item from the list
                        // ---------------------------------------
                        // Notice that we need to remove from the Model (the array)
                        // and not from the ListView (the View)!
                        // The view will be updated after the  notifyDataSetChanged();
                        mValues.remove(clicked);
                        mAdapter.notifyDataSetChanged();

                    } else {
                        startActivity(launchIntent);
                    }
                } else {
                    // nothing to do here
                    Toast.makeText(getBaseContext(), "Activity is not attached", Toast.LENGTH_LONG).show();
                }
            } else {
                Intent intent = new Intent(getBaseContext(), clicked.getActivity());
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
    };
    private RecyclerView.LayoutManager mLayoutManager;

    public MainActivity() {
        AsyncHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // creates the db if it's the first time
                // just loads otherwise
                DbHelper.getDb(MainActivity.this);
            }
        }, 1000, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // +v4.1
        // setting the toolbar view to act as the app toolbar (named previously as "ActionBar")
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainList = (RecyclerView) findViewById(R.id.my_list);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mainList.setLayoutManager(mLayoutManager);

        mAdapter = new ExercisesAdapter(mValues, mClickListener);

        // attach the data to the list view
        mainList.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // I want to read from file (properties), and it's really BAD to do it on the main thread.
        AsyncHandler.post(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final String userName = prefs.getString("display_name", "");
                if (!userName.isEmpty()) {
                    UiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            TextView tv = (TextView) findViewById(R.id.label);
                            if (tv != null) {
                                tv.setText(userName + "'s Examples");
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lesson4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mi_settings) {
            // Toast.makeText(getBaseContext(), "Todo: settings screen", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//
//    private class RowViewHolder extends RecyclerView.ViewHolder {
//        public RowViewHolder(View itemView) {
//            super(itemView);
//        }
//    }
}
