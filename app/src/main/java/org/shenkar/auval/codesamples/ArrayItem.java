package org.shenkar.auval.codesamples;

/**
 * Model: data only
 *
 * there is nothing related to view here. (and it shouldn't, because we keep
 * the model in memory longer than a lifecycle of an Activity).
 *
 * Created by amir on 11/29/16.
 */
class ArrayItem {
    private Class activity;
    private String label;
    private String packageName;

    public ArrayItem(String label, Class activity) {
        this.label = label;
        this.activity = activity;
    }

    public ArrayItem(String label, String packageName) {
        this.label = label;
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        if (activity != null) {
            return "Open " + label;
        }
        return label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * optional parameter, to launch a different app (as an example)
     */
    public String getPackageName() {
        return packageName;
    }

    public Class getActivity() {
        return activity;
    }
}
