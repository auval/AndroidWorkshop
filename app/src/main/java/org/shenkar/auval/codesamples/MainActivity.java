package org.shenkar.auval.codesamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        mainList = (ListView) findViewById(R.id.my_list);

        ArrayItem[] values = new ArrayItem[]{
                new ArrayItem("Lecture 2: Activities communicate", Lesson2Activity.class),
                new ArrayItem("Lecture 3: This menu", null),
        };

        ArrayAdapter<ArrayItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);

        mainList.setAdapter(adapter);

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
