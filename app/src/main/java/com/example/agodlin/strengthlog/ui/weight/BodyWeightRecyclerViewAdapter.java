package com.example.agodlin.strengthlog.ui.weight;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agodlin.strengthlog.ui.common.SwipeDeleteAdapter;
import com.example.agodlin.strengthlog.ui.common.SwipeDeleteStubViewHolder;
import com.example.agodlin.strengthlog.ui.exercises.ExerciseNameRecyclerViewAdapter;
import com.example.agodlin.strengthlog.ui.weight.BodyWeightFragment.OnListFragmentInteractionListener;
import com.example.agodlin.strengthlog.R;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BodyWeightItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BodyWeightRecyclerViewAdapter extends SwipeDeleteAdapter<BodyWeightItem> {

    public BodyWeightRecyclerViewAdapter(List<BodyWeightItem> items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipe_delete, parent, false);
        return new ViewHolder(view, R.layout.fragment_bodyweight);
    }

    @Override
    public void onBindViewHolderInner(RecyclerView.ViewHolder holder2, int position){
        ViewHolder holder = (ViewHolder) holder2;
        holder.mItem = this.mItems.get(position);
        holder.mIdView.setText(holder.mItem.date.toString());
        holder.mContentView.setText(String.valueOf(holder.mItem.weight));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public class ViewHolder extends SwipeDeleteStubViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public BodyWeightItem mItem;

        public ViewHolder(View view, int layoutId)
        {
            super(view, layoutId);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.weight);
            mContentView = (TextView) view.findViewById(R.id.date);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
