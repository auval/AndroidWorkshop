package org.shenkar.auval.codesamples;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.transition.ChangeBounds;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.util.Random;


public class SvgViewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_views);

        // start animating the butterflies!
        animateButterfly(R.id.butterfly1);
        animateButterfly(R.id.butterfly2);
    }

    private void animateButterfly(int resourceId) {
        ImageView bf = (ImageView) findViewById(resourceId);
        AnimationDrawable frameAnimation = (AnimationDrawable) bf.getDrawable();
        frameAnimation.setCallback(bf);
        frameAnimation.setVisible(true, true);
        frameAnimation.start();

    }


    public void onButterflyClicked(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        /*
        android:layout_marginLeft="144dp"
        android:layout_marginStart="144dp"
        android:layout_marginTop="114dp"*/
        int width = parent.getWidth();
        int height = parent.getHeight();
        Random rand = new Random();

        layoutParams.leftMargin = rand.nextInt(width);
        layoutParams.topMargin = rand.nextInt(height);
        view.setLayoutParams(layoutParams);

        // fly!
//        Transition transition = new ArcMotion(); // todo: support nicer path: branch on API version
        Transition transition = new ChangeBounds();
        transition.setDuration(5000); // todo: make the duration a function of the distance
        transition.setInterpolator(new LinearInterpolator());
        TransitionManager.beginDelayedTransition(parent, transition);
    }


}
