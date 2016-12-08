package org.shenkar.auval.codesamples;

import android.os.Bundle;
import android.support.transition.ChangeBounds;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;

import org.shenkar.auval.codesamples.databinding.ActivityScene1Binding;
import org.shenkar.auval.codesamples.databinding.ActivityScene2Binding;

/**
 * Checkout the official training for scene transitions here:
 * https://developer.android.com/training/transitions/index.html
 * <p>
 * checkout the official training on data binding here:
 * https://developer.android.com/topic/libraries/data-binding/index.html
 *
 * @author amir uval
 */
public class SceneTransitionActivity extends AppCompatActivity {

    ActivityScene1Binding as1Bind;
    ActivityScene2Binding as2Bind;
    Scene mScene1;
    Scene mScene2;
    Transition mTransition;
    TransitionManager mTransitionManager;
    MyData myData;
    ViewGroup rootView;
    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            myData.setMyEdit(editable.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * This is how to get a reference to the root view
         */
        rootView = (ViewGroup) findViewById(android.R.id.content);


        as1Bind = ActivityScene1Binding.inflate(getLayoutInflater(), rootView, false);
        mScene1 = new Scene(rootView, as1Bind.getRoot());
        mScene1.enter();


        as2Bind = ActivityScene2Binding.inflate(getLayoutInflater(), null, false);
        mScene2 = new Scene(rootView, as2Bind.getRoot());

        // bind data
        myData = new MyData("Hello Scene Transition", "Press \"Scene 2\"");
        as1Bind.setMydata(myData);
        as2Bind.setMydata(myData);

        as1Bind.editText.addTextChangedListener(tw);
        as2Bind.editText.addTextChangedListener(tw);


        mTransitionManager = new TransitionManager();
        mTransition = new ChangeBounds();
    }

    public void onGotoScene1(View view) {
        mTransitionManager.setTransition(mScene2, mScene1, mTransition);
        mTransitionManager.transitionTo(mScene1);
    }

    public void onGotoScene2(View view) {
        mTransitionManager.setTransition(mScene1, mScene2, mTransition);
        mTransitionManager.transitionTo(mScene2);
    }
}
