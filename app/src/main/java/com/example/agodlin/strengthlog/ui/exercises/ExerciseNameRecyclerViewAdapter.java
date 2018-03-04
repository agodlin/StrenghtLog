package com.example.agodlin.strengthlog.ui.exercises;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.ui.common.SwipeDeleteAdapter;
import com.example.agodlin.strengthlog.ui.common.SwipeDeleteStubViewHolder;
import com.example.agodlin.strengthlog.ui.exercises.ExercisesFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link String} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
    * https://github.com/nemanja-kovacevic/recycler-view-swipe-to-delete/
    * https://github.com/ashrithks/SwipeRecyclerView
 */
public class ExerciseNameRecyclerViewAdapter extends SwipeDeleteAdapter<String> {
    int mLayoutId;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipe_delete, parent, false);
        return new ExerciseViewHolder(view, mLayoutId);
    }

    public ExerciseNameRecyclerViewAdapter(List<String> items, OnListFragmentInteractionListener listener, int layoutId) {
        super(items, listener);
        mLayoutId = layoutId;
    }

    @Override
    public void onBindViewHolderInner(RecyclerView.ViewHolder holder, int position){
        ExerciseViewHolder viewHolder = (ExerciseViewHolder)holder;
        viewHolder.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final String item = mItems.get(position);
        viewHolder.titleTextView.setText(item.toString());
    }

    public static class ExerciseViewHolder extends SwipeDeleteStubViewHolder
    {
        TextView titleTextView;
        public ExerciseViewHolder(View parent, int layoutId)
        {
            super(parent, layoutId);
            titleTextView = (TextView)parent.findViewById(R.id.title_text_view);
        }
    }
}
