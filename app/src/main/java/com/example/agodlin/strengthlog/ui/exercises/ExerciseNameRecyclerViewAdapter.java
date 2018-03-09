package com.example.agodlin.strengthlog.ui.exercises;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.ui.common.SwipeViewHolder;
import com.example.agodlin.strengthlog.ui.exercises.ExercisesFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link String} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
    * https://github.com/nemanja-kovacevic/recycler-view-swipe-to-delete/
    * https://github.com/ashrithks/SwipeRecyclerView
 */
public class ExerciseNameRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseNameRecyclerViewAdapter.ExerciseViewHolder> {
    OnListFragmentInteractionListener mListener;
    List<String> mItems;
    @Override
    public ExerciseNameRecyclerViewAdapter.ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_swipe_delete, parent, false);
        return new ExerciseViewHolder(view);
    }

    public ExerciseNameRecyclerViewAdapter(List<String> items, OnListFragmentInteractionListener listener) {
        mListener = listener;
        mItems = items;
    }

    @Override
    public void onBindViewHolder(final ExerciseNameRecyclerViewAdapter.ExerciseViewHolder holder, int position){
        holder.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListFragmentInteraction(holder.titleTextView.getText().toString());
            }
        });
        final String item = mItems.get(position);
        holder.titleTextView.setText(item.toString());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ExerciseViewHolder extends SwipeViewHolder
    {
        TextView titleTextView;
        public ExerciseViewHolder(View parent)
        {
            super(parent, R.layout.row_view);
            titleTextView = (TextView)parent.findViewById(R.id.title_text_view);
        }
    }
}
