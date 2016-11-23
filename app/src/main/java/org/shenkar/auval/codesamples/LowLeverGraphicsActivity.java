package org.shenkar.auval.codesamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

public class LowLeverGraphicsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_lever_graphics);

        // randomize the animation
        MyAnimatedGraphicsView animatedGraphicsView = (MyAnimatedGraphicsView) findViewById(R.id.animated_graphics);
        MyBitmapAnimatedGraphicsView bitmapAnimatedGraphicsView = (MyBitmapAnimatedGraphicsView) findViewById(R.id.animated_graphics_buffer);

        Random random = new Random();
        int i1 = 2 + random.nextInt(10); // 2..12
        int i2 = 1 + random.nextInt(10); // 1..11
        animatedGraphicsView.setAnimationParams(i1 * 1000, i2 * 1000);
        bitmapAnimatedGraphicsView.setAnimationParams(i1 * 1000, i2 * 1000);


    }


}
