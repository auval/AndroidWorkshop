package org.shenkar.auval.codesamples;

import android.databinding.BaseObservable;

/**
 * Created by amir on 12/8/16.
 */

public class MyData extends BaseObservable {
    private String myEdit;
    private String myText;
    private String myTitle;

    public MyData(String myText, String myTitle) {
        this.myText = myText;
        this.myTitle = myTitle;
    }

    public String getMyEdit() {
        return myEdit;
    }

    public void setMyEdit(String myEdit) {
        this.myEdit = myEdit;
        // updates the binder - that sets the new value into the scenes
        notifyChange();
    }

    public String getMyText() {
        return myText;
    }

    public String getMyTitle() {
        return myTitle;
    }
}
