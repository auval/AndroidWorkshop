package org.shenkar.auval.codesamples;

import android.os.Bundle;
import android.support.transition.Scene;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Checkout the official training for scene transitions here:
 * https://developer.android.com/training/transitions/index.html
 *
 * @author amir uval
 */
public class SceneTransitionActivity extends AppCompatActivity {
    ViewGroup rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_1);

        /**
         * This is how to get a reference to the root view
         */
        rootView = (ViewGroup) findViewById(android.R.id.content);

    }

    public void onGotoScene1(View view) {
        /**
         * "go" will cause:
         * 1) inflate the new layout
         * 2) calculate the layout
         * 3) check for each view the differences between now and the next layout
         * 4) create an animator
         * 5) animate
         */
        Scene mScene1 = Scene.getSceneForLayout(rootView, R.layout.activity_scene_1, getBaseContext());
        TransitionManager.go(mScene1);
    }

    public void onGotoScene2(View view) {
        Scene mScene2 = Scene.getSceneForLayout(rootView, R.layout.activity_scene_2, getBaseContext());
        TransitionManager.go(mScene2);
    }
}
