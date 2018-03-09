package com.example.agodlin.strengthlog.ui.workout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.agodlin.strengthlog.R;
import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.ui.common.SwipeViewHolder;
import com.example.agodlin.strengthlog.ui.weight.BodyWeightItem;
import com.example.agodlin.strengthlog.ui.workout.WorkoutsFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Date} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private final OnListFragmentInteractionListener mListener;
    List<Date> mItems;
    public MyItemRecyclerViewAdapter(List<Date> items, OnListFragmentInteractionListener listener) {
        mListener = listener;
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_swipe_delete, parent, false);
            return new ViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_swipe_delete, parent, false);
        return new LoadingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mItem = mItems.get(position);
            viewHolder.mIdView.setText(mItems.get(position).toString());

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(viewHolder.mItem);
                    }
                }
            });
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void removeItem(int position) {
        mItems.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Date item, int position) {
        mItems.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class ViewHolder extends SwipeViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Date mItem;

        public ViewHolder(View view) {
            super(view, R.layout.fragment_item);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    static class LoadingViewHolder extends SwipeViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView, R.layout.loading_items);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
}
