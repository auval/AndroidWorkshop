package org.shenkar.auval.codesamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView mainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // +v4.1
        // setting the toolbar view to act as the app toolbar (named previously as "ActionBar")
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainList = (ListView) findViewById(R.id.my_list);

        // define data for the list
        ArrayItem[] values = new ArrayItem[]{
                new ArrayItem("Lecture 2: Activities communicate", Lesson2Activity.class),
                new ArrayItem("Lecture 3: App Toolbar", ActionbarExampleActivity.class),
        };

        // add the data to an adapter
        ArrayAdapter<ArrayItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);

        // attach the data to the list view
        mainList.setAdapter(adapter);

        // wait for click events (this is a non-blocking call!)
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ArrayItem clicked = (ArrayItem) mainList.getItemAtPosition(position);
                if (clicked.activity == null) {
                    // nothing to do here
                    Toast.makeText(getBaseContext(), "Activity is not attached", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getBaseContext(), clicked.activity);
                    startActivity(intent);
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
            Toast.makeText(getBaseContext(), "Todo: settings screen", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    static class ArrayItem {
        private Class activity;
        private String label;

        public ArrayItem(String label, Class activity) {
            this.label = label;
            this.activity = activity;
        }

        @Override
        public String toString() {
            if (activity != null) {
                return "Open " + label;
            }
            return label;
        }
    }
}
