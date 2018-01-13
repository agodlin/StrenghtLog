package com.example.agodlin.strengthlog.ui.exercise;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.ui.exercise.dummy.DummyContent;

import java.util.List;

/**
 * Created by agodlin on 1/13/2018.
 */

public class ExerciseCardRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseCardRecyclerViewAdapter.ViewHolder> {

    private final List<Exercise> mValues;
    private final ExerciseFragment.OnListFragmentInteractionListener mListener;

    public ExerciseCardRecyclerViewAdapter(List<Exercise> items, ExerciseFragment.OnListFragmentInteractionListener listener) {
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
        holder.name.setText(mValues.get(position).name);
        holder.age.setText(mValues.get(position).date.toString());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView age;
        public Exercise mItem;
        public ViewHolder(View view, Context context) {
            super(view);
            name = (TextView)itemView.findViewById(R.id.person_name);
            age = (TextView)itemView.findViewById(R.id.person_age);
        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
}