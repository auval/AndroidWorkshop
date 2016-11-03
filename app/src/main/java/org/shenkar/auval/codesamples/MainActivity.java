package org.shenkar.auval.codesamples;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Lesson 2 example:
 * - Relative Layout
 * - findViewById
 * - setting listeners
 * <p>
 * Warning: Hard-coded strings and not RTL in mind yet
 *
 * @author Amir
 */
public class MainActivity extends AppCompatActivity {
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.the_name);

        final View b1 = findViewById(R.id.button1);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    b1.setEnabled(true);
                } else {
                    b1.setEnabled(false);
                }
            }
        });
        final View b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBooDelayed(String.valueOf(name.getText()));
                name.setText("Oh no!");
            }
        });
    }

    /**
     * the "final" in the parameter allows the variable to be accessible from the anonymous class
     *
     * @param nameString
     */
    private void showBooDelayed(final String nameString) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), BooActivity.class);
                if (!nameString.isEmpty()) {
                    intent.putExtra("name", nameString);
                }
                startActivity(intent);
            }
        }, 1000);
    }

    public void buttonWasPressed(View v) {
        Toast.makeText(this, "hello " + name.getText(), Toast.LENGTH_LONG).show();
    }


}
