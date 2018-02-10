package com.example.agodlin.strengthlog.ui.workout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.common.Set;

import java.util.List;

/**
 * Created by agodlin on 2/1/2018.
 */

public class WorkoutCardRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutCardRecyclerViewAdapter.ViewHolder>{
    private final List<Set> mValues;
    private final WorkoutFragment.OnListFragmentInteractionListener mListener;

    public WorkoutCardRecyclerViewAdapter(List<Set> items, WorkoutFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public WorkoutCardRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_cardview_list_item, parent, false);
        return new WorkoutCardRecyclerViewAdapter.ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(WorkoutCardRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.setInfo.setText(mValues.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView setInfo;
        public Set mItem;
        public ViewHolder(View view, Context context) {
            super(view);
            setInfo = (TextView)itemView.findViewById(R.id.set_info);
        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
}
