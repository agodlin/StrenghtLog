package com.example.agodlin.strengthlog.ui.exercise;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.common.Set;
import com.example.agodlin.strengthlog.ui.common.SwipeViewHolder;
import com.example.agodlin.strengthlog.ui.exercises.ExerciseFragment;
import com.example.agodlin.strengthlog.ui.weight.BodyWeightItem;

import java.util.List;

/**
 * Created by agodlin on 1/13/2018.
 */

public class ExerciseCardRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseCardRecyclerViewAdapter.ViewHolder> {

    private final List<Set> mValues;

    public ExerciseCardRecyclerViewAdapter(List<Set> items) {
        mValues = items;
    }

    @Override
    public ExerciseCardRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_swipe_delete, parent, false);
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

    public void removeItem(int position) {
        mValues.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Set item, int position) {
        mValues.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class ViewHolder extends SwipeViewHolder {
        public final TextView setInfo;
        public Set mItem;
        public ViewHolder(View view, Context context) {
            super(view, R.layout.exercise_cardview_list_item);
            setInfo = (TextView)itemView.findViewById(R.id.set_info);
        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
}