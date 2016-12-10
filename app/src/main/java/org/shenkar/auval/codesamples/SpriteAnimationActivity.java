package org.shenkar.auval.codesamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SpriteAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprite_animation);

    }

    public void onBlockButtonClicked(View view) {
        // this is something you obviously must not do on the UI thread!
        // it's for demonstration purpose only.
        // after 5 seconds of this, the app will display ANR (App Not Responding) and ask the user
        // to shut the app down.
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
