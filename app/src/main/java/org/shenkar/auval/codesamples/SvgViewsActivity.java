package org.shenkar.auval.codesamples;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.transition.ChangeBounds;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import java.util.Random;

public class SvgViewsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_svg_views);


//        ConstraintLayout contentView = (ConstraintLayout) findViewById(R.id.activity_svg_views);
//        addButterflyCompat(contentView);

        // start animating the butterflies!
        animateButterfly(R.id.butterfly_1);
        animateButterfly(R.id.butterfly_2);
    }

//    private void addButterflyCompat(ConstraintLayout contentView) {
//
//        android.support.v7.widget.AppCompatImageView iv = new android.support.v7.widget
//                .AppCompatImageView(this);
//        ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(50,50);
////                ConstraintLayout.LayoutParams.WRAP_CONTENT,
////                ConstraintLayout.LayoutParams.WRAP_CONTENT);
//        newParams.leftMargin = 50;
//        newParams.topMargin = 50;
//        contentView.addView(iv, -1, newParams);
//
//
//        AnimatedVectorDrawableCompat bf =
//                AnimatedVectorDrawableCompat.create(this, R.drawable.butterfly0);
//
//       // bf.get
//
////        iv.setImageDrawable(bf);
//        iv.setBackgroundDrawable(bf);
//
//        bf.setCallback(iv);
//
//        bf.start();
//
//    }

    private void animateButterfly(int resourceId) {
//        ImageView bf = (ImageView) findViewById(resourceId);

        AppCompatImageView bv = (AppCompatImageView) findViewById(resourceId);
        bv.setOnClickListener(this);

        Drawable drawable1 = bv.getDrawable();
        drawable1.setCallback(bv);

        if (drawable1 instanceof AnimatedVectorDrawableCompat) {
            AnimatedVectorDrawableCompat drawable = (AnimatedVectorDrawableCompat) drawable1;
            drawable.start();
        } else {
            AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) drawable1;
            drawable.start();
        }

//        AnimationDrawable frameAnimation = (AnimationDrawable) bf.getDrawable();
//        frameAnimation.setCallback(bf);
//        frameAnimation.setVisible(true, true);
//        frameAnimation.start();

//        VectorDrawableCo frame = frameAnimation.getFrame(0);


    }


    @Override
    public void onClick(View view) {
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
