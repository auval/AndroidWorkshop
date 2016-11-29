package org.shenkar.auval.codesamples;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Reference training:
 * https://developer.android.com/training/material/lists-cards.html
 * <p>
 * Created by amir on 11/29/16.
 */

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.MyViewHolder> {
    private View.OnClickListener listener;
    private ArrayList<ArrayItem> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExercisesAdapter(ArrayList<ArrayItem> myDataset,
                            View.OnClickListener listener) {
        mDataset = myDataset;
        this.listener = listener;

        /**
         * Stable Id's are making change animation possible!
         * together with a unique getItemId() per row
         */
        setHasStableIds(true);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ArrayItem arrayItem = mDataset.get(position);
        holder.mTextView.setText(arrayItem.toString());
        holder.mTextView.setTag(arrayItem); // a handy way to pass any data to a view
        holder.mTextView.setOnClickListener(listener);

    }

    @Override
    public long getItemId(int position) {
        return mDataset.get(position).hashCode();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        // will assign a click listener to it
//        private ViewGroup vg;

        public MyViewHolder(TextView v) {
            super(v);
            mTextView = v;
//            mTextView = (TextView) v.findViewById(R.id.row_text);
//            vg = v;
        }
    }
}

