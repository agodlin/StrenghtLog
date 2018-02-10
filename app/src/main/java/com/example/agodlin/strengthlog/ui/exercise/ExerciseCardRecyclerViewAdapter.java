package com.example.agodlin.strengthlog.ui.exercise;

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
 * Created by agodlin on 1/13/2018.
 */

public class ExerciseCardRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseCardRecyclerViewAdapter.ViewHolder> {

    private final List<Set> mValues;
    private final ExerciseFragment.OnListFragmentInteractionListener mListener;

    public ExerciseCardRecyclerViewAdapter(List<Set> items, ExerciseFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ExerciseCardRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_cardview_item, parent, false);
        return new ExerciseCardRecyclerViewAdapter.ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ExerciseCardRecyclerViewAdapter.ViewHolder holder, int position) {
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