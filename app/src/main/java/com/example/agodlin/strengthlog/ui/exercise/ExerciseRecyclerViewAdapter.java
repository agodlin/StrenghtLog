package com.example.agodlin.strengthlog.ui.exercise;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.db.DataManager;
import com.example.agodlin.strengthlog.ui.exercise.ExerciseFragment.OnListFragmentInteractionListener;
import com.example.agodlin.strengthlog.ui.exercise.dummy.DummyContent;
import com.example.agodlin.strengthlog.ui.exercise.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;

    public ExerciseRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_exercise, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        List<String> tmp = new ArrayList<String>(DataManager.ITEM_MAP.keySet());
        String name = tmp.get(position);
        holder.recyclerView.setAdapter(new ExerciseCardRecyclerViewAdapter(DataManager.ITEM_MAP.get(name), mListener));
        holder.recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataManager.ITEM_MAP.keySet().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final RecyclerView recyclerView;

        public ViewHolder(View view, Context context) {
            super(view);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.list);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
}
