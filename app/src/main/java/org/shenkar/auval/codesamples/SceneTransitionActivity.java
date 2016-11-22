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
    Scene mScene1;
    Scene mScene2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_1);

        /**
         * This is how to get a reference to the root view
         */
        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);

        mScene1 = Scene.getSceneForLayout(rootView, R.layout.activity_scene_1, getBaseContext());
        mScene2 = Scene.getSceneForLayout(rootView, R.layout.activity_scene_2, getBaseContext());

    }

    public void onGotoScene1(View view) {

        TransitionManager.go(mScene1);
    }

    public void onGotoScene2(View view) {
        TransitionManager.go(mScene2);

    }
}
